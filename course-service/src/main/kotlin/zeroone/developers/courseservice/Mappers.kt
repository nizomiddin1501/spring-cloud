package zeroone.developers.courseservice

import org.springframework.stereotype.Component

@Component
class CourseMapper {

    fun toDto(course: Course): CourseResponse {
        return course.run {
            CourseResponse(
                id = this.id,
                name = this.name,
                description = this.description,
                price = this.price
            )
        }
    }

    fun toEntity(createRequest: CourseCreateRequest): Course {
        return createRequest.run {
            Course(
                name = this.name,
                description = this.description,
                price = this.price
            )
        }
    }

    fun updateEntity(course: Course, updateRequest: CourseUpdateRequest): Course {
        return updateRequest.run {
            course.apply {
                updateRequest.name.let { this.name = it }
                updateRequest.description.let { this.description = it }
                updateRequest.price.let { this.price = it }
            }
        }
    }
}







