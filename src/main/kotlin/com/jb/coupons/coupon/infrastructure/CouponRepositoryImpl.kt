package com.jb.coupons.coupon.infrastructure

import com.jb.coupons.coupon.domain.Coupon
import com.jb.coupons.coupon.domain.CouponRepository
import org.springframework.stereotype.Repository

@Repository
class CouponRepositoryImpl(
    private val couponEntityRepository: CouponEntityRepository
) : CouponRepository {

    override fun save(coupon: Coupon): Coupon {

        val entity = coupon.toEntity()
        val saved = couponEntityRepository.save(entity)

        return Coupon(
            saved.code,
            coupon.createdAt,
            coupon.maxUsages,
            coupon.actualUsage,
            coupon.country
        )
    }

}