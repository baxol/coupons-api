package com.jb.coupons

import com.fasterxml.jackson.databind.ObjectMapper
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
class IntegrationTest : TestcontainersTest() {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var clock: Clock

    fun simplePost(path: String, body: Any, status: HttpStatus, response: Any) {
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

    fun simplePostResultStatus(path: String, body: Any): Int =
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post(path)
                    .content(objectMapper.writeValueAsString(body))
                    .contentType("application/json")
            )
            .andReturn()
            .response
            .status


}