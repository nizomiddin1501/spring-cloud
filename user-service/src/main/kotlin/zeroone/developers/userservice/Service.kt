package zeroone.developers.userservice

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.math.BigDecimal
interface UserService {
    fun getAll(page: Int, size: Int): Page<UserResponse>
    fun getAll(): List<UserResponse>
    fun getOne(id: Long): UserResponse
    fun getUserEntity(id: Long): User
    fun create(request: UserCreateRequest)
    fun update(id: Long, request: UserUpdateRequest)
    fun getUserBalance(id: Long): BigDecimal
    fun fillBalance(userId: Long, amount: BigDecimal): BigDecimal
    fun deductBalance(userId: Long, amount: BigDecimal): Boolean
    fun getUserStats(): UserStatsResponse
    fun delete(id: Long)
}

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) : UserService {

    override fun getAll(page: Int, size: Int): Page<UserResponse> {
        val pageable: Pageable = PageRequest.of(page, size)
        val usersPage = userRepository.findAllNotDeletedForPageable(pageable)
        return usersPage.map { userMapper.toDto(it) }
    }

    override fun getAll(): List<UserResponse> {
        return userRepository.findAllNotDeleted().map {
            userMapper.toDto(it)
        }
    }

    override fun getOne(id: Long): UserResponse {
        userRepository.findByIdAndDeletedFalse(id)?.let {
            return userMapper.toDto(it)
        } ?: throw UserNotFoundException()
    }

    override fun getUserEntity(id: Long): User {
        return userRepository.findByIdAndDeletedFalse(id)
            ?: throw UserNotFoundException()
    }

    override fun create(request: UserCreateRequest) {
        val existingUser = userRepository.findByUsernameAndDeletedFalse(request.username)
        if (existingUser != null) throw UserAlreadyExistsException()
        userRepository.save(userMapper.toEntity(request))
    }

    override fun update(id: Long, request: UserUpdateRequest) {
        val user = userRepository.findByIdAndDeletedFalse(id) ?: throw UserNotFoundException()
        userRepository.findByUsername(id, request.username)?.let { throw UserAlreadyExistsException() }

        val updateUser = userMapper.updateEntity(user, request)
        userRepository.save(updateUser)
    }


    override fun getUserBalance(id: Long): BigDecimal {
        val user = userRepository.findById(id).orElseThrow { UserNotFoundException() }
        return user.balance
    }

    override fun fillBalance(userId: Long, amount: BigDecimal): BigDecimal {
        val user = userRepository.findById(userId).orElseThrow { UserNotFoundException() }
        user.balance += amount
        userRepository.save(user)
        return user.balance
    }

    override fun deductBalance(userId: Long, amount: BigDecimal): Boolean {
        val user = userRepository.findById(userId).orElseThrow { UserNotFoundException() }
        if (user.balance >= amount) {
            user.balance -= amount
            userRepository.save(user)
            return true
        }
        return false
    }

    override fun getUserStats(): UserStatsResponse {
        val totalUsers = userRepository.count()
        val totalBalance = userRepository.sumBalance()
        return UserStatsResponse(totalUsers, totalBalance)
    }

    override fun delete(id: Long) {
        userRepository.trash(id) ?: throw UserNotFoundException()
    }
}





