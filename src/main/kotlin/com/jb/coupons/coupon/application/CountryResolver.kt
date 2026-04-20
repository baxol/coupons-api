package com.jb.coupons.coupon.application

interface CountryResolver {
    fun resolve(ipAddress: String): String
}