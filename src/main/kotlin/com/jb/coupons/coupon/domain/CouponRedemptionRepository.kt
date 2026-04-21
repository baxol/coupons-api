package com.jb.coupons.coupon.domain

interface CouponRedemptionRepository {
    fun save(couponRedemption: CouponRedemption): CouponRedemption
    fun existsByUserIdAndCouponCode(userId: UserId, code: CouponCode): Boolean
}