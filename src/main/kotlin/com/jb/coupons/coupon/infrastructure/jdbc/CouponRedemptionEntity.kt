package com.jb.coupons.coupon.infrastructure.jdbc

import com.jb.coupons.coupon.domain.CouponRedemption
import org.springframework.data.annotation.Id
import java.time.Instant

data class CouponRedemptionEntity(
    @Id
    val id: Int?,
    val couponId: Int,
    val userRef: String,
    val createdAt: Instant
)

fun CouponRedemption.toEntity(id: Int? = null, couponId: Int) =
    CouponRedemptionEntity(
        id,
        couponId,
        userId,
        createdAt
    )

fun CouponRedemptionEntity.toDomain(code: String) =
    CouponRedemption.create(
        userRef,
        code,
        createdAt
    )