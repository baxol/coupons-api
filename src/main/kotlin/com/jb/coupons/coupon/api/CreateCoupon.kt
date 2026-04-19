package com.jb.coupons.coupon.api

import java.time.Instant

data class CreateCouponRequest(
    val code: String?,
    val maxUsages: Int,
    val country: String
)


data class CreateCouponResponse(
    val code: String,
    val createdAt: Instant,
    val maxUsages: Int,
    val country: String
)
