package com.jb.coupons.coupon.infrastructure.persistance

import org.springframework.data.repository.CrudRepository
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

interface JdbcCouponRedemptionRepository : CrudRepository<CouponRedemptionEntity, Int>,
    JdbcCouponRedemptionRepositoryCustom {
}

interface JdbcCouponRedemptionRepositoryCustom {
    fun existsByUserIdAndCouponCode(userId: String, code: String): Boolean
}


@Repository
class JdbcCouponRedemptionRepositoryCustomImpl(
    private val jdbc: NamedParameterJdbcTemplate

) : JdbcCouponRedemptionRepositoryCustom {

    override fun existsByUserIdAndCouponCode(userId: String, code: String): Boolean =
        jdbc.queryForObject(
            """
            SELECT count (*)
            FROM coupon_redemption_entity
            JOIN coupon_entity ON coupon_entity.id = coupon_redemption_entity.coupon_id
            WHERE coupon_redemption_entity.user_ref = CAST(:userId as text)
              AND coupon_entity.code = CAST(:code AS citext)
    """.trimIndent(),
            mapOf(
                "userId" to userId,
                "code" to code
            ),
            Int::class.java
        )!! > 0

}