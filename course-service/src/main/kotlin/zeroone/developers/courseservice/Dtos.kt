package zeroone.developers.courseservice

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.annotation.Nonnull
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

data class BaseMessage(val code : Int, val message : String?)


@Schema(description = "Data transfer object for User createRequest")
data class UserCreateRequest(

    @Schema(description = "Unique username", example = "nizomiddin097")
    var username: String,

    @Schema(description = "User's password", example = "root123")
    var password: String,

    @Schema(description = "User role", example = "USER")
    @Enumerated(EnumType.STRING)
    var role: UserRole = UserRole.USER,

    @Schema(description = "User balance", example = "100.0")
    var balance: BigDecimal
)


@Schema(description = "Data transfer object for User createRequest")
data class UserResponse(

    @Schema(description = "User ID", example = "1")
    val id: Long?,

    @Schema(description = "Unique username", example = "nizomiddin097")
    var username: String?,

    @Schema(description = "User's password", example = "root123")
    var password: String?,

    @Schema(description = "User role", example = "USER")
    @Enumerated(EnumType.STRING)
    var role: UserRole?,

    @Schema(description = "User balance", example = "100.0")
    var balance: BigDecimal?
)


@Schema(description = "Data transfer object for User createRequest")
data class UserUpdateRequest(

    @Column(unique = true, nullable = false)
    @Schema(description = "Unique username", example = "nizomiddin097")
    var username: String,

    @Column(nullable = false)
    @Schema(description = "User's password", example = "root123")
    var password: String,

    @Schema(description = "User role", example = "USER")
    @Enumerated(EnumType.STRING)
    var role: UserRole,

    @Schema(description = "User balance", example = "100.0")
    var balance: BigDecimal
)


@Schema(description = "Data transfer object for Course createRequest")
data class CourseCreateRequest(

    @Schema(description = "Course name", example = "Kotlin Programming")
    val name: String,

    @Schema(description = "Course description", example = "A complete guide to Kotlin programming language")
    val description: String,

    @Schema(description = "Course price", example = "150.0")
    val price: BigDecimal
)


@Schema(description = "Data transfer object for Course response")
data class CourseResponse(

    @Schema(description = "Course ID", example = "1")
    val id: Long?,

    @Schema(description = "Course name", example = "Kotlin Programming")
    val name: String?,

    @Schema(description = "Course description", example = "A complete guide to Kotlin programming language")
    val description: String?,

    @Schema(description = "Course price", example = "150.0")
    val price: BigDecimal?
)


@Schema(description = "Data transfer object for Course updateRequest")
data class CourseUpdateRequest(

    @Schema(description = "Course name", example = "Kotlin Programming")
    var name: String,

    @Schema(description = "Course description", example = "A complete guide to Kotlin programming language")
    var description: String,

    @Schema(description = "Course price", example = "150.0")
    var price: BigDecimal
)


@Schema(description = "Data transfer object for Payment createRequest")
data class PaymentCreateRequest(

    @Schema(description = "Amount paid", example = "150.00")
    var amount: BigDecimal,

    @Schema(description = "Payment date and time", example = "2024-11-24T10:15:30")
    var paymentDate: LocalDateTime = LocalDateTime.now(),

    @Schema(description = "Payment method used", example = "CREDIT_CARD")
    var paymentMethod: PaymentMethod,

    @Schema(description = "Payment status", example = "SUCCESS")
    var status: Status
)



@Schema(description = "Data transfer object for Payment response")
data class PaymentResponse(

    @Schema(description = "Payment ID", example = "1")
    val id: Long?,

    @Schema(description = "Amount paid", example = "150.00")
    var amount: BigDecimal?,

    @Schema(description = "Payment date and time", example = "2024-11-24T10:15:30")
    var paymentDate: LocalDateTime = LocalDateTime.now(),

    @Schema(description = "Payment method used", example = "CREDIT_CARD")
    var paymentMethod: PaymentMethod?,

    @Schema(description = "Payment status", example = "PENDING")
    var status: Status?,

    @Schema(description = "User name (retrieved from User service)", example = "John Doe")
    val userName: String? = null,

    @Schema(description = "Course name (retrieved from Course service)", example = "Kotlin Programming")
    val courseName: String? = null
)


@Schema(description = "Data transfer object for Payment updateRequest")
data class PaymentUpdateRequest(

    @Schema(description = "Amount paid", example = "150.00")
    var amount: BigDecimal,

    @Schema(description = "Payment date and time", example = "2024-11-24T10:15:30")
    var paymentDate: LocalDateTime = LocalDateTime.now(),

    @Schema(description = "Payment method used", example = "CREDIT_CARD")
    var paymentMethod: PaymentMethod,

    @Schema(description = "Payment status", example = "SUCCESS")
    var status: Status
)


data class UserStatsResponse(
    val totalUsers: Long,
    val totalBalance: BigDecimal
)

data class CourseStatsResponse(
    val totalCourses: Long,
    val totalIncome: BigDecimal
)

data class PaymentStatsResponse(
    val totalPayments: Long,
    val totalAmountPaid: BigDecimal
)








