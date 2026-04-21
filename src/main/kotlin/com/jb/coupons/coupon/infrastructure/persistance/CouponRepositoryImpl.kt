package com.jb.coupons.coupon.infrastructure.persistance

import com.jb.coupons.coupon.domain.Coupon
import com.jb.coupons.coupon.domain.CouponAlreadyExistsException
import com.jb.coupons.coupon.domain.CouponCode
import com.jb.coupons.coupon.domain.CouponRepository
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Repository

@Repository
class CouponRepositoryImpl(
    private val jdbcCouponRepository: JdbcCouponRepository
) : CouponRepository {

    override fun incrementUsageIfAvailable(code: CouponCode): Boolean =
        jdbcCouponRepository.increaseUsageIfAvailable(code.value)

    override fun save(coupon: Coupon): Coupon =
        coupon.toEntity()
            .let {
                try {
                    jdbcCouponRepository.save(it)
                } catch (e: DuplicateKeyException) {
                    throw CouponAlreadyExistsException()
                }
            }
            .toDomain()

    override fun findByCodeIgnoreCase(code: CouponCode): Coupon? =
        jdbcCouponRepository.findByCode(code.value)
            ?.toDomain()

    override fun existsByCodeIgnoreCase(code: CouponCode): Boolean =
        jdbcCouponRepository.existsByCode(code.value)

}