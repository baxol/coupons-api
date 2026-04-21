package com.jb.coupons.coupon.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class CouponCodeTest {

    @Test
    fun `should create coupon code with trimmed value`() {
        val couponCode = CouponCode.create("  coupon1  ")

        assertEquals("coupon1", couponCode.value)
    }

    @Test
    fun `should throw when coupon code is blank`() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            CouponCode.create("   ")
        }

        assertEquals("Code can not be blank", exception.message)
    }

}