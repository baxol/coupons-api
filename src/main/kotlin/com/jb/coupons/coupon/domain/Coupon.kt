package com.jb.coupons.coupon.domain

import java.time.Instant

@JvmInline
value class CouponCode private constructor(
    val value: String,
) {
    companion object {
        fun create(code: String): CouponCode {
            val clean = code.trim()

            require(clean.isNotBlank()) {
                "Code can not be blank"
            }

            return CouponCode(clean)
        }
    }
}

class Coupon(
    val code: CouponCode,
    val createdAt: Instant,
    val maxUsages: Int,
    val actualUsage: Int,
    val country: Country
) {

    companion object {
        fun create(code: String, createdAt: Instant, maxUsages: Int, country: String): Coupon {
            require(maxUsages > 0) { "Max usage must be > 0" }
            return Coupon(
                CouponCode.create(code),
                createdAt,
                maxUsages,
                0,
                Country.create(country)
            )
        }
    }

    fun canBeUsed(userCountry: Country): Result<Unit> =
        when {
            country != userCountry ->
                Result.failure(CouponError.InvalidCountry())

            else -> Result.success(Unit)
        }
}

sealed class CouponError : Throwable() {
    class InvalidCountry : CouponError()
}