package com.jb.coupons.coupon.domain

import java.time.Instant

class CouponRedemption private constructor(
    val userId: String,
    val code: CouponCode,
    val createdAt: Instant,
) {

    companion object {
        fun create(userId: String, code: String, createdAt: Instant): CouponRedemption {
            return CouponRedemption(userId, CouponCode.create(code), createdAt)
        }
    }
}