package com.jb.coupons.coupon.infrastructure.persistance

import com.jb.coupons.common.DataCouponNotFoundException
import com.jb.coupons.coupon.domain.CouponCode
import com.jb.coupons.coupon.domain.CouponRedemption
import com.jb.coupons.coupon.domain.CouponRedemptionRepository
import com.jb.coupons.coupon.domain.UserId
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
        userId: UserId,
        code: CouponCode
    ): Boolean =
        jdbcCouponRedemptionRepository.existsByUserIdAndCouponCode(userId.value, code.value)

}