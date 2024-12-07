package zeroone.developers.paymentservice

import org.springframework.stereotype.Component


@Component
class PaymentMapper {

    fun toDto(payment: Payment, userName: String? = null, courseName: String? = null): PaymentResponse {
        return payment.run {
            PaymentResponse(
                id = this.id,
                userId = this.userId,
                courseId = this.courseId,
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
                userId = this.userId,
                courseId = this.courseId,
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







