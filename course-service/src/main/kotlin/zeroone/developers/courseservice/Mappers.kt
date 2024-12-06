package zeroone.developers.courseservice

import org.springframework.stereotype.Component

@Component
class UserMapper {
    fun toDto(user: User): UserResponse {
        return user.run {
            UserResponse(
                id = this.id,
                username = this.username,
                password = this.password,
                role = this.role,
                balance = this.balance
            )
        }
    }

    fun toEntity(createRequest: UserCreateRequest): User {
        return createRequest.run {
            User(
                username = this.username,
                password = this.password,
                role = this.role,
                balance = this.balance
            )
        }
    }

    fun updateEntity(user: User, updateRequest: UserUpdateRequest): User {
        return updateRequest.run {
            user.apply {
                updateRequest.username.let { this.username = it }
                updateRequest.password.let { this.password = it }
                updateRequest.role.let { this.role = it }
                updateRequest.balance.let { this.balance = it }
            }
        }
    }
}


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



@Component
class PaymentMapper {

    fun toDto(payment: Payment, userName: String? = null, courseName: String? = null): PaymentResponse {
        return payment.run {
            PaymentResponse(
                id = this.id,
                amount = this.amount,
                paymentDate = this.paymentDate,
                paymentMethod = this.paymentMethod,
                status = this.status,
                userName = userName,
                courseName = courseName
            )
        }
    }

    fun toEntity(createRequest: PaymentCreateRequest): Payment {
        return createRequest.run {
            Payment(
                amount = this.amount,
                paymentDate = this.paymentDate,
                paymentMethod = this.paymentMethod,
                status = this.status
            )
        }
    }

    fun updateEntity(payment: Payment, updateRequest: PaymentUpdateRequest): Payment {
        return updateRequest.run {
            payment.apply {
                updateRequest.amount.let { this.amount = it }
                updateRequest.paymentDate.let { this.paymentDate = it }
                updateRequest.paymentMethod.let { this.paymentMethod = it }
                updateRequest.status.let { this.status = it }
            }
        }
    }
}





