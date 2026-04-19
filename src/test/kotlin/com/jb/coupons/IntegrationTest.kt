package com.jb.coupons

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.ClassOrderer
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestClassOrder
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.Clock

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestClockConfig::class)
@TestClassOrder(ClassOrderer.OrderAnnotation::class)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class IntegrationTest {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var clock: Clock

    fun simplePostCompareJson(path: String, body: Any, status: HttpStatus, response: Any) {
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post(path)
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(body))
            )
            .andExpect(MockMvcResultMatchers.status().`is`(status.value()))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(response)))
    }

}