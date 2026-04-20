package com.jb.coupons.coupon.domain

import com.jb.coupons.common.ApiException
import com.jb.coupons.common.ErrorMessage
import com.jb.coupons.common.ErrorType
import org.springframework.http.HttpStatus

sealed class CouponDomainException(
    httpStatus: HttpStatus,
    errorMessage: ErrorMessage,
    errorType: ErrorType = ErrorType.INCONSISTENT_DATA
) : ApiException(httpStatus, errorType, errorMessage)

class CouponNotFoundException() : CouponDomainException(
    HttpStatus.NOT_FOUND,
    ErrorMessage.COUPON_NOT_FOUND,
    ErrorType.RESOURCE_NOT_FOUND
)

class CouponLimitReachedException() : CouponDomainException(
    HttpStatus.CONFLICT,
    ErrorMessage.COUPON_LIMIT_REACHED,
)

class InvalidCouponCountryException() : CouponDomainException(
    HttpStatus.FORBIDDEN,
    ErrorMessage.COUPON_NOT_ALLOWED,
)

class CouponAlreadyExistsException() : CouponDomainException(
    HttpStatus.CONFLICT,
    ErrorMessage.COUPON_ALREADY_EXISTS,
)

class CouponAlreadyUsedException() : CouponDomainException(
    HttpStatus.CONFLICT,
    ErrorMessage.COUPON_ALREADY_USED,
)