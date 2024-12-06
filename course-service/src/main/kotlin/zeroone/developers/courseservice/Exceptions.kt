package zeroone.developers.courseservice

import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.context.support.ResourceBundleMessageSource
import zeroone.developers.courseservice.BaseMessage

sealed class MicroserviceExceptionHandler() : RuntimeException() {
    abstract fun errorCode(): ErrorCodes
    open fun getArguments(): Array<Any?>? = null

    fun getErrorMessage(resourceBundleMessageSource: ResourceBundleMessageSource): BaseMessage {
        val message = try {
            resourceBundleMessageSource.getMessage(
                errorCode().name, getArguments(), LocaleContextHolder.getLocale()
            )
        } catch (e: Exception) {
            e.message ?: "Unknown error"
        }
        return BaseMessage(errorCode().code, message)
    }
}

class UserAlreadyExistsException : MicroserviceExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.USER_ALREADY_EXISTS
    }
}

class UserNotFoundException : MicroserviceExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.USER_NOT_FOUND
    }
}

class CourseAlreadyExistsException : MicroserviceExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.COURSE_ALREADY_EXISTS
    }
}

class CourseNotFoundException : MicroserviceExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.COURSE_NOT_FOUND
    }
}


class PaymentAlreadyExistsException : MicroserviceExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.PAYMENT_ALREADY_EXISTS
    }
}

class PaymentNotFoundException : MicroserviceExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.PAYMENT_NOT_FOUND
    }
}


class UserAccessDeniedException : MicroserviceExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.ROLE_ACCESS_DENIED
    }
}

class CannotCancelOrderException : MicroserviceExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.CANNOT_CANCEL_ORDER
    }
}

class InvalidOrderStatusException : MicroserviceExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.INVALID_ORDER_STATUS
    }
}

class InvalidPaymentMethodException : MicroserviceExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.PAYMENT_METHOD_NOT_FOUND
    }
}

class InsufficientBalanceException : MicroserviceExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.INSUFFICIENT_BALANCE
    }
}







