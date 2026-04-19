package com.jb.coupons

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName


@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
abstract class TestcontainersTest {

    companion object {

        val POSTGRES_SQL_CONTAINER: PostgreSQLContainer<*> = PostgreSQLContainer(DockerImageName.parse("postgres:16.1"))
                .withDatabaseName("backend_db")
                .withUsername("postgres")
                .withPassword("postgres")

        @JvmStatic
        @DynamicPropertySource
        fun postgreSQLContainerProperties(registry: DynamicPropertyRegistry) {
            POSTGRES_SQL_CONTAINER.start()
            registry.add("spring.datasource.url") { POSTGRES_SQL_CONTAINER.jdbcUrl }
            registry.add("spring.datasource.username") { POSTGRES_SQL_CONTAINER.username }
            registry.add("spring.datasource.password") { POSTGRES_SQL_CONTAINER.password }
        }

    }
}
