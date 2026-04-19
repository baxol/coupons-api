package com.jb.coupons.coupon.domain

interface CouponRepository {
    fun save(coupon: Coupon): Coupon
}