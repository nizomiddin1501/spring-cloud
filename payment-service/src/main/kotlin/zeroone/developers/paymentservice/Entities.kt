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
    @Column(nullable = false) var deleted: Boolean = false
)


@Table
@Entity(name = "payments")
@Schema(description = "Payment details")
class Payment(

    @Column(nullable = false)
    @Schema(description = "User ID", example = "1")
    var userId: Long,

    @Column(nullable = false)
    @Schema(description = "Course ID", example = "3")
    var courseId: Long,

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


