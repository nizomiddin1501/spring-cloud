package zeroone.developers.courseservice
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import lombok.RequiredArgsConstructor
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler(private val errorMessageSource: ResourceBundleMessageSource) {

    @ExceptionHandler(MicroserviceExceptionHandler::class)
    fun handleAccountException(exception: MicroserviceExceptionHandler): ResponseEntity<BaseMessage> {
        return ResponseEntity.badRequest().body(exception.getErrorMessage(errorMessageSource))
    }
}



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
class CourseController(val service: CourseService) {


    @Operation(summary = "Get all courses", description = "Fetches all courses from the database.")
    @ApiResponses(ApiResponse(responseCode = "200", description = "Successfully fetched all courses"))
    @GetMapping
    fun getAll() = service.getAll()


    @Operation(summary = "Get all courses with pagination", description = "Fetches courses with pagination support.")
    @ApiResponses(ApiResponse(responseCode = "200", description = "Successfully fetched paginated courses"))
    @GetMapping("/page")
    fun getAll(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int) =
        service.getAll(page, size)


    @Operation(summary = "Get course by ID", description = "Fetches a single course based on the provided ID.")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Successfully fetched the course"),
        ApiResponse(responseCode = "404", description = "Course not found"))
    @GetMapping("{id}")
    fun getOne(@PathVariable id: Long) = service.getOne(id)


    @Operation(summary = "Create new course", description = "Creates a new course record.")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Course successfully created"),
        ApiResponse(responseCode = "400", description = "Invalid request data"),
        ApiResponse(responseCode = "403", description = "Access Denied"))
    @PostMapping
    fun create(@RequestBody @Valid request: CourseCreateRequest,
               @RequestHeader("userId") userId: Long) {
        service.create(request, userId)
    }


    @Operation(summary = "Update existing course", description = "Updates an existing course based on the provided ID.")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Course successfully updated"),
        ApiResponse(responseCode = "404", description = "Course not found"),
        ApiResponse(responseCode = "400", description = "Invalid request data"),
        ApiResponse(responseCode = "403", description = "Access Denied"))
    @PutMapping("{id}")
    fun update(@PathVariable id: Long,
               @RequestBody @Valid request: CourseUpdateRequest,
               @RequestHeader("userId") userId: Long) {
        service.update(id, request, userId)
    }

    @Operation(summary = "Get course statistics",description = "Fetches statistical information about all courses.")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Successfully fetched course statistics"))
    @GetMapping("/stats")
    fun getCourseStats(): CourseStatsResponse {
        return service.getCourseStats()
    }

    @Operation(summary = "Purchase a course", description = "Allows the user to purchase a course.")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Course successfully purchased"),
        ApiResponse(responseCode = "404", description = "User or Course not found"),
        ApiResponse(responseCode = "400", description = "Insufficient balance or invalid data"),
        ApiResponse(responseCode = "403", description = "Access Denied"))
    @PostMapping("/purchase/{courseId}")
    fun purchaseCourse(
        @PathVariable courseId: Long,
        @RequestHeader("userId") userId: Long): PaymentResponse {
        return service.purchaseCourse(userId, courseId)
    }

    @Operation(summary = "Delete course by ID", description = "Deletes a course based on the provided ID.")
    @ApiResponses(
        ApiResponse(responseCode = "204", description = "Course successfully deleted"),
        ApiResponse(responseCode = "404", description = "Course not found"),
        ApiResponse(responseCode = "403", description = "Access Denied"))
    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long,
               @RequestHeader("userId") userId: Long) {
        service.delete(id, userId)
    }
}













