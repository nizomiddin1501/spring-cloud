package zeroone.developers.courseservice
import jakarta.validation.Valid
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@FeignClient(name = "user-service", url="http://localhost:8081/api/users")
interface UserService {


    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): UserResponse // Get user by ID


    @GetMapping("/{id}/balance")
    fun getUserBalance(@PathVariable id: Long): BigDecimal // Get user balance


    @PostMapping("/{id}/deduct")
    fun deductBalance(
        @PathVariable id: Long,
        @RequestParam amount: BigDecimal): Boolean


}


@FeignClient(name = "payment-service", url="http://localhost:8083/api/payments")
interface PaymentService {


    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): UserResponse // Get user by ID


    @PostMapping
    fun create(
        @RequestBody @Valid request: PaymentCreateRequest,
        @RequestHeader("userId") userId: Long,
        @RequestParam courseId: Long): PaymentResponse

}




