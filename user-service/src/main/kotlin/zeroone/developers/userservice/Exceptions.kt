package zeroone.developers.userservice

import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.context.support.ResourceBundleMessageSource

sealed class BillingExceptionHandler() : RuntimeException() {
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

class UserAlreadyExistsException : BillingExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.USER_ALREADY_EXISTS
    }
}

class UserNotFoundException : BillingExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.USER_NOT_FOUND
    }
}

class CourseAlreadyExistsException : BillingExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.COURSE_ALREADY_EXISTS
    }
}

class CourseNotFoundException : BillingExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.COURSE_NOT_FOUND
    }
}


class PaymentAlreadyExistsException : BillingExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.PAYMENT_ALREADY_EXISTS
    }
}

class PaymentNotFoundException : BillingExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.PAYMENT_NOT_FOUND
    }
}


class UserAccessDeniedException : BillingExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.ROLE_ACCESS_DENIED
    }
}

class CannotCancelOrderException : BillingExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.CANNOT_CANCEL_ORDER
    }
}

class InvalidOrderStatusException : BillingExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.INVALID_ORDER_STATUS
    }
}

class InvalidPaymentMethodException : BillingExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.PAYMENT_METHOD_NOT_FOUND
    }
}

class InsufficientBalanceException : BillingExceptionHandler() {
    override fun errorCode(): ErrorCodes {
        return ErrorCodes.INSUFFICIENT_BALANCE
    }
}







