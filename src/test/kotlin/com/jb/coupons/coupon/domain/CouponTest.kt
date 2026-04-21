package com.jb.coupons.coupon.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Instant

class CouponTest {

    val createdAt: Instant = Instant.parse("2026-04-21T14:15:00Z")

    @Test
    fun `should create coupon`() {
        val coupon = Coupon.create(" coupon1 ", createdAt, 5, "pl")

        assertEquals("coupon1", coupon.code.value)
        assertEquals(createdAt, coupon.createdAt)
        assertEquals(5, coupon.maxUsages)
        assertEquals(0, coupon.actualUsage)
        assertEquals("PL", coupon.country.code)
    }

    @Test
    fun `should throw when max usages is less than one`() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            Coupon.create("coupon1", createdAt, 0, "PL")
        }

        assertEquals("Max usage must be > 0", exception.message)
    }

    @Test
    fun `should allow using coupon when user country matches`() {
        val coupon = Coupon.create("coupon1", createdAt, 5, "PL")

        val result = coupon.canBeUsed(Country.create("PL"))

        assertTrue(result.isSuccess)
    }

    @Test
    fun `should return invalid country error when user country does not match`() {
        val coupon = Coupon.create("coupon1", createdAt, 5, "PL")

        val result = coupon.canBeUsed(Country.create("DE"))

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is CouponError.InvalidCountry)
    }

}