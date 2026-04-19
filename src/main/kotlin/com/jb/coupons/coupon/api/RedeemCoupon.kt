package com.jb.coupons.coupon.api

data class RedeemCouponRequest(
    val userId: String,
    val coupon: String
)

class RedeemCouponResponse(
    val success: Boolean,
    val message: String? = null
)