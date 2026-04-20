package com.jb.coupons.coupon.infrastructure

import com.jb.coupons.common.DataCouponNotFoundException
import com.jb.coupons.coupon.domain.CouponCode
import com.jb.coupons.coupon.domain.CouponRedemption
import com.jb.coupons.coupon.domain.CouponRedemptionRepository
import com.jb.coupons.coupon.infrastructure.jdbc.JdbcCouponRedemptionRepository
import com.jb.coupons.coupon.infrastructure.jdbc.JdbcCouponRepository
import com.jb.coupons.coupon.infrastructure.jdbc.toDomain
import com.jb.coupons.coupon.infrastructure.jdbc.toEntity
import org.springframework.stereotype.Repository

@Repository
class CouponRedemptionRepositoryImpl(
    private val jdbcCouponRepository: JdbcCouponRepository,
    private val jdbcCouponRedemptionRepository: JdbcCouponRedemptionRepository
) : CouponRedemptionRepository {

    override fun save(couponRedemption: CouponRedemption): CouponRedemption {
        val couponEntity = jdbcCouponRepository.findByCode(couponRedemption.code.value)
            ?: throw DataCouponNotFoundException()

        return couponRedemption.toEntity(couponId = couponEntity.id!!)
            .let { jdbcCouponRedemptionRepository.save(it) }
            .toDomain(couponEntity.code)
    }

    override fun existsByUserIdAndCouponCode(
        userId: String,
        code: CouponCode
    ): Boolean =
        jdbcCouponRedemptionRepository.existsByUserIdAndCouponCode(userId, code.value)

}