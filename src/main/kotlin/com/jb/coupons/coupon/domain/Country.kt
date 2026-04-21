package com.jb.coupons.coupon.domain

import java.util.*

@JvmInline
value class Country private constructor(
    val code: String
) {
    companion object {
        private val ISO_CODES = Locale.getISOCountries().toSet()

        fun create(code: String): Country {
            val clean = code.trim().uppercase()

            require(clean.length == 2) { "Country code must have exactly 2: $clean" }
            require(ISO_CODES.contains(clean)) { "Unrecognized country code: $clean" }

            return Country(clean)
        }
    }

}