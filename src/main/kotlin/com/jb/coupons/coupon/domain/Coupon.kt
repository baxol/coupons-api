package com.jb.coupons.coupon.domain

import java.time.Instant

data class Coupon(
    val code: String,
    val createdAt: Instant,
    val maxUsages: Int,
    val actualUsage: Int,
    val country: String
)