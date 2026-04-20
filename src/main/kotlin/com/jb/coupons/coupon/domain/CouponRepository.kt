package com.jb.coupons.coupon.domain

interface CouponRepository {
    fun incrementUsageIfAvailable(code: CouponCode): Boolean
    fun save(coupon: Coupon): Coupon
    fun findByCodeIgnoreCase(code: CouponCode): Coupon?
    fun existsByCodeIgnoreCase(code: CouponCode): Boolean
}