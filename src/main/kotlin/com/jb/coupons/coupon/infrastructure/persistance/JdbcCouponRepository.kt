package com.jb.coupons.coupon.infrastructure.persistance

import org.springframework.data.repository.CrudRepository
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

interface JdbcCouponRepository : CrudRepository<CouponEntity, Int>, JdbcCouponRepositoryCustom {
    fun findByCode(code: String): CouponEntity?
}

interface JdbcCouponRepositoryCustom {
    fun existsByCode(code: String): Boolean
    fun increaseUsageIfAvailable(code: String): Boolean
}

@Repository
class JdbcCouponRepositoryCustomImpl(
    private val jdbc: NamedParameterJdbcTemplate
) : JdbcCouponRepositoryCustom {

    override fun existsByCode(code: String): Boolean =
        jdbc.queryForObject(
            """
            SELECT count (*)
            FROM coupon_entity
            WHERE code = CAST(:code AS citext)
    """.trimIndent(),
            mapOf("code" to code),
            Int::class.java
        )!! > 0

    override fun increaseUsageIfAvailable(code: String): Boolean {
        val updatedRows = jdbc.update(
            """
        UPDATE coupon_entity
        SET actual_usage = actual_usage + 1
        WHERE code = CAST(:code AS citext)
          AND actual_usage < max_usages
    """.trimIndent(),
            mapOf("code" to code)
        )

        return updatedRows == 1
    }

}