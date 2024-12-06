package zeroone.developers.paymentservice

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal
import java.util.*
import jakarta.persistence.*
import lombok.NoArgsConstructor
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID", example = "1")
    var id: Long? = null,
    @CreatedDate @Temporal(TemporalType.TIMESTAMP) var createdDate: Date? = null,
    @LastModifiedDate @Temporal(TemporalType.TIMESTAMP) var modifiedDate: Date? = null,
    @CreatedBy var createdBy: Long? = null,
    @LastModifiedBy var lastModifiedBy: Long? = null,
    //@Column(nullable = false) @ColumnDefault(value = "false") var deleted: Boolean = false
    @Column(nullable = false) var deleted: Boolean = false
)


@NoArgsConstructor
@Table
@Entity(name = "users")
@Schema(description = "User information")
class User(

    @Column(unique = true, nullable = false)
    @Schema(description = "Unique username", example = "nizomiddin097")
    var username: String,

    @Column(nullable = false)
    @Schema(description = "User's password", example = "root123")
    var password: String,

    @Schema(description = "User role", example = "USER")
    @Enumerated(EnumType.STRING)
    var role: UserRole = UserRole.USER,

    @Schema(description = "User balance", example = "100.0")
    var balance: BigDecimal
) : BaseEntity()
//{
//    constructor() : this("", "", UserRole.USER, BigDecimal.ZERO)
//}


@Table
@Entity(name = "course")
@Schema(description = "Payment details")
class Course(

    @Column(nullable = false)
    @Schema(description = "Course name", example = "Kotlin Programming")
    var name: String,

    @Column(nullable = false)
    @Schema(description = "Course description", example = "A complete guide to Kotlin programming language")
    var description: String,

    @Column(nullable = false)
    @Schema(description = "Course price", example = "150.0")
    var price: BigDecimal
) : BaseEntity()
//{
//    constructor() : this("", "", BigDecimal.ZERO)
//}


@Table
@Entity(name = "payments")
@Schema(description = "Payment details")
class Payment(

    @Column(nullable = false)
    @Schema(description = "Amount paid", example = "150.00")
    var amount: BigDecimal,

    @Column(nullable = false)
    @Schema(description = "Payment date and time", example = "2024-11-24T10:15:30")
    var paymentDate: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Schema(description = "Payment method used", example = "CREDIT_CARD")
    var paymentMethod: PaymentMethod,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Schema(description = "Payment status", example = "SUCCESS")
    var status: Status
) : BaseEntity()
//{
//    constructor() : this(
//        BigDecimal.ZERO, LocalDateTime.now(),
//        PaymentMethod.CREDIT_CARD, Status.SUCCESS
//    )
//}


//@Entity
//@Schema(description = "User information")
//data class User(
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Schema(description = "User ID", example = "1")
//    val id: Long? = null,
//
//    @Schema(description = "User first name", example = "John")
//    val firstName: String,
//
//    @Schema(description = "User last name", example = "Doe")
//    val lastName: String,
//
//    @Schema(description = "User email address", example = "john.doe@example.com")
//    val email: String,
//
//    @Schema(description = "User phone number", example = "+998901234567")
//    val phoneNumber: String,
//
//    @Schema(description = "User password", example = "password123")
//    val password: String
//)












