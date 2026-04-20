package com.jb.coupons.common;

import org.springframework.http.HttpStatus

data class ApiError(
    val status: Int,
    val error: String,

    val type: ErrorType,
    val message: ErrorMessage?,
    val detail: Any?
) {
    constructor(
        httpStatus: HttpStatus,
        errorType: ErrorType,
        errorMessage: ErrorMessage? = null,
        detail: Any? = null,
    ) : this(
        httpStatus.value(),
        httpStatus.reasonPhrase,
        errorType,
        errorMessage,
        detail
    )
}


enum class ErrorType {
    UNKNOWN,

    BAD_REQUEST,
    RESOURCE_NOT_FOUND,

    INCONSISTENT_DATA,

}

enum class ErrorMessage {
    INTERNAL_SERVER_ERROR,

    COUPON_ALREADY_EXISTS,
    COUPON_NOT_ALLOWED,
    COUPON_NOT_FOUND,
    COUPON_LIMIT_REACHED,
    COUPON_ALREADY_USED
}


open class ApiException(
    val httpStatus: HttpStatus,
    val errorType: ErrorType,
    val errorMessage: ErrorMessage,
    val detail: Any? = null
) : RuntimeException(errorMessage.toString())

class DataCouponNotFoundException() :
    ApiException(
        HttpStatus.NOT_FOUND,
        ErrorType.INCONSISTENT_DATA,
        ErrorMessage.COUPON_NOT_FOUND
    )