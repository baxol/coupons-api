package com.jb.coupons.coupon.domain

interface CouponRepository {
    fun create(coupon: Coupon)
    fun redeem(userId: String, couponCode: String)
}