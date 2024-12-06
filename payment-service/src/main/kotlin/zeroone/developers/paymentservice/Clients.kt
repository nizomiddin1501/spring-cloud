package zeroone.developers.paymentservice

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@FeignClient(name = "user-service", url="http://localhost:8081/api/users")
interface UserService {

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): UserResponse // Get user by ID

}

@FeignClient(name = "course-service", url="http://localhost:8082/api/courses")
interface CourseService {


    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): CourseResponse // Get user by ID



}
