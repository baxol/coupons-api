package com.jb.coupons.coupon.infrastructure.jdbc

import com.jb.coupons.coupon.domain.Coupon
import com.jb.coupons.coupon.domain.CouponCode
import org.springframework.data.annotation.Id
import java.time.Instant

data class CouponEntity(
    @Id
    val id: Int?,
    val code: String,
    val createdAt: Instant,
    val maxUsages: Int,
    val actualUsage: Int,
    val country: String
)

fun Coupon.toEntity(id: Int? = null) = CouponEntity(
    id,
    code.value,
    createdAt,
    maxUsages,
    actualUsage,
    country
)

fun CouponEntity.toDomain() = Coupon(
    CouponCode.create(code),
    createdAt,
    maxUsages,
    actualUsage,
    country
)