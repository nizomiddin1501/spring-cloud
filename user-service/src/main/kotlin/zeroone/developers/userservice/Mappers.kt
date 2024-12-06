package zeroone.developers.userservice

import org.springframework.stereotype.Component

@Component
class UserMapper {
    fun toDto(user: User): UserResponse {
        return user.run {
            UserResponse(
                id = this.id,
                username = this.username,
                password = this.password,
                role = this.role,
                balance = this.balance
            )
        }
    }

    fun toEntity(createRequest: UserCreateRequest): User {
        return createRequest.run {
            User(
                username = this.username,
                password = this.password,
                role = this.role,
                balance = this.balance
            )
        }
    }

    fun updateEntity(user: User, updateRequest: UserUpdateRequest): User {
        return updateRequest.run {
            user.apply {
                updateRequest.username.let { this.username = it }
                updateRequest.password.let { this.password = it }
                updateRequest.role.let { this.role = it }
                updateRequest.balance.let { this.balance = it }
            }
        }
    }
}












