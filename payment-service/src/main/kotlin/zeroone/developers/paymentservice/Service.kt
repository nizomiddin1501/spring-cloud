package zeroone.developers.paymentservice
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.math.BigDecimal

interface PaymentService {
    fun getAll(page: Int, size: Int): Page<PaymentResponse>
    fun getOne(id: Long): PaymentResponse
    fun create(request: PaymentCreateRequest): PaymentResponse
    fun getPaymentsByUserId(userId: Long): List<PaymentResponse>
    fun getPaymentStats(): PaymentStatsResponse
}

@Service
class PaymentServiceImpl(
    private val paymentRepository: PaymentRepository,
    private val paymentMapper: PaymentMapper,
    private val userService: UserService,
    private val courseService: CourseService
) : PaymentService {

    // Get all payments with pagination
    override fun getAll(page: Int, size: Int): Page<PaymentResponse> {
        val paymentPage = paymentRepository.findAll(PageRequest.of(page, size))
        return paymentPage.map { paymentMapper.toDto(it) }
    }

    override fun getOne(id: Long): PaymentResponse {
        val payment = paymentRepository.findById(id)
            .orElseThrow { PaymentNotFoundException() }
        return paymentMapper.toDto(payment)
    }

    // Create new payment
    override fun create(request: PaymentCreateRequest): PaymentResponse {
        val userResponse = userService.getOne(request.userId) ?: throw UserNotFoundException()
        val courseResponse = courseService.getOne(request.courseId) ?: throw CourseNotFoundException()
        val payment = paymentMapper.toEntity(request)
        val savedPayment = paymentRepository.save(payment)
        return paymentMapper.toDto(savedPayment, userResponse.username, courseResponse.name)
    }


    // Get all paymenmvn ts by user ID
    override fun getPaymentsByUserId(userId: Long): List<PaymentResponse> {
        val payments = paymentRepository.findAll()
        val userPayments = payments.filter { payment ->
            val user = userService.getOne(userId)
            user.id == userId
        }
        return userPayments.map { paymentMapper.toDto(it) }
    }


    // Get payment statistics
    override fun getPaymentStats(): PaymentStatsResponse {
        val totalPayments = paymentRepository.count()
        val totalAmountPaid = paymentRepository.sumAmountPaid()
        return PaymentStatsResponse(totalPayments, totalAmountPaid ?: BigDecimal.ZERO)
    }


}

