package zeroone.developers.paymentservice
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import lombok.RequiredArgsConstructor
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler(private val errorMessageSource: ResourceBundleMessageSource) {

    @ExceptionHandler(PaymentExceptionHandler::class)
    fun handleAccountException(exception: PaymentExceptionHandler): ResponseEntity<BaseMessage> {
        return ResponseEntity.badRequest().body(exception.getErrorMessage(errorMessageSource))
    }
}



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
class PaymentController(
    private val paymentService: PaymentService
) {


    // Get all payments with pagination
    @Operation(summary = "Get all payments", description = "Retrieves all payments with pagination.")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Payments found"),
        ApiResponse(responseCode = "404", description = "No payments found"))
    @GetMapping
    fun getAllPayments(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int): Page<PaymentResponse> {
        return paymentService.getAll(page, size)
    }


    @Operation(summary = "Get payment by ID", description = "Retrieves a payment record based on the provided ID.")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Payment found"),
        ApiResponse(responseCode = "404", description = "Payment not found"))
    @GetMapping("{id}")
    fun getPaymentById(@PathVariable id: Long): PaymentResponse {
        return paymentService.getOne(id)
    }


    @Operation(summary = "Create new payment", description = "Creates a new payment record.")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Payment successfully created"),
        ApiResponse(responseCode = "400", description = "Invalid request data"))
    @PostMapping
    fun createPayment(
        @RequestBody @Valid request: PaymentCreateRequest, ): PaymentResponse {
        return paymentService.create(request)
    }


    @Operation(summary = "Get all payments by user ID", description = "Retrieves all payments made by a specific user.")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Payments found"),
        ApiResponse(responseCode = "404", description = "No payments found for the user"))
    @GetMapping("/user/{userId}")
    fun getPaymentsByUserId(@PathVariable userId: Long): List<PaymentResponse> {
        return paymentService.getPaymentsByUserId(userId)
    }


    @Operation(summary = "Get payment statistics", description = "Retrieves the total number of payments and the total amount paid.")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Payment statistics found"))
    @GetMapping("/stats")
    fun getPaymentStats(): PaymentStatsResponse {
        return paymentService.getPaymentStats()
    }
}







