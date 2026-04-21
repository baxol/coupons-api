package com.jb.coupons.coupon.api

import com.jb.coupons.coupon.domain.CouponRedemption
import jakarta.validation.constraints.NotBlank
import java.time.Instant

data class RedeemCouponRequest(
    @field:NotBlank
    val userId: String,

    @field:NotBlank
    val code: String
)

class RedeemCouponResponse(
    val code: String
)

fun RedeemCouponRequest.toDomain(createdAt: Instant) =
    CouponRedemption.create(userId, code, createdAt)

fun CouponRedemption.toDto() =
    RedeemCouponResponse(code.value)