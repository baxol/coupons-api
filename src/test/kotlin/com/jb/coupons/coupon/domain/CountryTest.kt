package com.jb.coupons.coupon.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class CountryTest {

    @Test
    fun `should create country with uppercase normalized code`() {
        val country = Country.create(" pl ")

        assertEquals("PL", country.code)
    }

    @Test
    fun `should throw when country code is blank`() {
        val exception =
            assertThrows(IllegalArgumentException::class.java) {
                Country.create(" ")
            }

        assertEquals("Country code must have exactly 2: ", exception.message)
    }

    @Test
    fun `should throw when country code length is not equal to 2`() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            Country.create("POLSKA")
        }

        assertEquals("Country code must have exactly 2: POLSKA", exception.message)
    }

    @Test
    fun `should throw when country code is not recognized`() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            Country.create("XX")
        }

        assertEquals("Unrecognized country code: XX", exception.message)
    }
}