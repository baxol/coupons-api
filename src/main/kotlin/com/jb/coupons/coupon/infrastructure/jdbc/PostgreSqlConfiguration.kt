package com.jb.coupons.coupon.infrastructure.jdbc

import org.postgresql.util.PGobject
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.transaction.annotation.EnableTransactionManagement


@Configuration
@EnableTransactionManagement
class PostgreSqlConfiguration(
) : AbstractJdbcConfiguration() {

    @Bean
    override fun userConverters(): List<Any> =
        listOf(
            PgObjectToStringConverter(),
        )

    @ReadingConverter
    class PgObjectToStringConverter : Converter<PGobject, String> {
        override fun convert(source: PGobject): String {
            return source.value ?: ""
        }
    }
}
