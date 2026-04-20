package com.jb.coupons.coupon.domain

interface CouponRedemptionRepository {
    fun save(couponRedemption: CouponRedemption): CouponRedemption
    fun existsByUserIdAndCouponCode(userId: String, code: CouponCode): Boolean
}