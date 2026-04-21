package com.jb.coupons.coupon.domain

import java.time.Instant

class CouponRedemption private constructor(
    val userId: UserId,
    val code: CouponCode,
    val createdAt: Instant,
) {

    companion object {
        fun create(userId: String, code: String, createdAt: Instant): CouponRedemption {
            return CouponRedemption(
                UserId(userId),
                CouponCode.create(code),
                createdAt
            )
        }
    }
}