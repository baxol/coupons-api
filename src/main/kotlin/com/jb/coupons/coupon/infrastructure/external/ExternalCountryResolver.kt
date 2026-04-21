package com.jb.coupons.coupon.infrastructure.external

import com.jb.coupons.common.CountryResolutionException
import com.jb.coupons.coupon.application.CountryResolver
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import java.net.InetAddress

@Component
class ExternalCountryResolver(
    restClientBuilder: RestClient.Builder,
    @Value("\${app.external.country-api-url}")
    private val baseUrl: String
) : CountryResolver {
    private val logger = LoggerFactory.getLogger(javaClass)

    private val restClient: RestClient =
        restClientBuilder
            .requestFactory(
                SimpleClientHttpRequestFactory()
                    .apply { setConnectTimeout(3000) }
                    .apply { setReadTimeout(3000) }
            )
            .build()

    @Cacheable("resolveCountry")
    override fun resolveCountry(ipAddress: String): String {
        if (!isPublicIp(ipAddress)) {
            throw CountryResolutionException("Ip address $ipAddress is not a public IP")
        }

        try {
            val response = restClient.get()
                .uri("$baseUrl/{ip}", ipAddress)
                .retrieve()
                .onStatus({ it.isError }) { _, res ->
                    throw CountryResolutionException("Country API - returned ${res.statusCode} for $ipAddress")
                }
                .body(CountryApiResponse::class.java)
                ?: throw CountryResolutionException("Country API - empty response")

            return response.country
        } catch (e: Exception) {
            if (e is CountryResolutionException) throw e
            logger.error("Failed to resolve country for IP: $ipAddress", e)
            throw CountryResolutionException("Country API - failed for: $ipAddress")
        }
    }

    private fun isPublicIp(ip: String): Boolean {
        if (!ip.all { it.isDigit() || it == '.' || it == ':' }) return false

        return try {
            val address = InetAddress.getByName(ip)
            !address.isSiteLocalAddress
                    && !address.isLoopbackAddress
                    && !address.isAnyLocalAddress
                    && !address.isLinkLocalAddress
        } catch (e: Exception) {
            false
        }
    }
}