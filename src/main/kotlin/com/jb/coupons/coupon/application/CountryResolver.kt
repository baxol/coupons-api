package com.jb.coupons.coupon.application

interface CountryResolver {
    fun resolveCountry(ipAddress: String): String
}