package zeroone.developers.courseservice

import org.springframework.context.annotation.Lazy
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime



interface CourseService {
    fun getAll(page: Int, size: Int): Page<CourseResponse>
    fun getAll(): List<CourseResponse>
    fun getOne(id: Long): CourseResponse
    fun checkAdminRole(userId: Long)
    fun create(request: CourseCreateRequest, userId: Long)
    fun update(id: Long, request: CourseUpdateRequest, userId: Long)
    fun getCourseStats(): CourseStatsResponse
    fun purchaseCourse(userId: Long, courseId: Long): PaymentResponse
    fun delete(id: Long, userId: Long)
}





@Service
class CourseServiceImpl(
    private val courseRepository: CourseRepository,
    private val courseMapper: CourseMapper,
    private val userService: UserService,
    @Lazy private val paymentService: PaymentService
) : CourseService {

    override fun getAll(page: Int, size: Int): Page<CourseResponse> {
        val pageable: Pageable = PageRequest.of(page-1, size)
        val coursesPage = courseRepository.findAllNotDeletedForPageable(pageable)
        return coursesPage.map { courseMapper.toDto(it) }
    }

    override fun getAll(): List<CourseResponse> {
        return courseRepository.findAllNotDeleted().map {
            courseMapper.toDto(it)
        }
    }

    override fun getOne(id: Long): CourseResponse {
        courseRepository.findByIdAndDeletedFalse(id)?.let {
            return courseMapper.toDto(it)
        } ?: throw CourseNotFoundException()
    }

    override fun checkAdminRole(userId: Long) {
        val user = userService.getOne(userId) ?: throw UserNotFoundException()
        if (user.role != UserRole.ADMIN) {
            throw UserAccessDeniedException()
        }
    }

    override fun create(request: CourseCreateRequest, userId: Long) {
        checkAdminRole(userId)
        val course = courseRepository.findByNameAndDeletedFalse(request.name)
        if (course != null) throw CourseAlreadyExistsException()
        courseRepository.save(courseMapper.toEntity(request))
    }

    override fun update(id: Long, request: CourseUpdateRequest, userId: Long) {
        checkAdminRole(userId)
        val course = courseRepository.findByIdAndDeletedFalse(id) ?: throw CourseNotFoundException()
        courseRepository.findByName(request.name)?.let { throw CourseAlreadyExistsException() }
        val updateCourse = courseMapper.updateEntity(course, request)
        courseRepository.save(updateCourse)
    }

    override fun getCourseStats(): CourseStatsResponse {
        val totalCourses = courseRepository.count()
        val totalIncome = courseRepository.sumCoursePrice()
        return CourseStatsResponse(totalCourses, totalIncome)
    }

    override fun purchaseCourse(userId: Long, courseId: Long): PaymentResponse {
        val userResponse = userService.getOne(userId) ?: throw UserNotFoundException()
        val courseResponse = getOne(courseId) ?: throw CourseNotFoundException()

        val isBalanceDeducted = userService.deductBalance(userId, courseResponse.price!!)
        if (!isBalanceDeducted) {
            throw InsufficientBalanceException()
        }
        val paymentCreateRequest = PaymentCreateRequest(
            amount = courseResponse.price,
            paymentDate = LocalDateTime.now(),
            paymentMethod = PaymentMethod.CREDIT_CARD,
            status = Status.SUCCESS
        )
        return paymentService.create(paymentCreateRequest, userId, courseId)
    }

    override fun delete(id: Long, userId: Long) {
        checkAdminRole(userId)
        courseRepository.trash(id) ?: throw CourseNotFoundException()
    }

}






