package com.jb.coupons

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

@TestConfiguration
class TestClockConfig {

    @Bean
    @Primary
    fun clock(): Clock = Clock.fixed(
        Instant.parse("2026-04-19T10:00:00Z"),
        ZoneOffset.UTC
    )

}