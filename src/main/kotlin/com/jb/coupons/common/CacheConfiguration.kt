package com.jb.coupons.common

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.util.concurrent.TimeUnit

@EnableCaching
@Configuration
class CacheConfiguration {

    @Bean
    @Primary
    fun cache1DayManager(): CacheManager =
        CaffeineCacheManager()
            .apply {  setCaffeine(
                Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.DAYS))
            }

}