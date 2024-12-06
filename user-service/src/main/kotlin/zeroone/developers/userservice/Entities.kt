package zeroone.developers.userservice

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import lombok.NoArgsConstructor
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

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
) : BaseEntity() {
    // Default constructor for JPA
    constructor() : this("", "", UserRole.USER, BigDecimal.ZERO)
}



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















