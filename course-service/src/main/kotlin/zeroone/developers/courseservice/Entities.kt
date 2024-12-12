package zeroone.developers.courseservice

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
    @Column(nullable = false) var deleted: Boolean = false
)



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




