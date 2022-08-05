package dev.code_n_roll.coroutines2

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.UUID

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = [
        "spring.datasource.url=jdbc:tc:postgresql:13.6:///databasename"
    ]
)
internal class TestControllerTest(
    @Autowired private val entityRepository: EntityRepository,
    @LocalServerPort private val localPort: Int
) {

    @Test
    fun `should retrieve entity`() {
        val id = UUID.randomUUID()
        entityRepository.save(TestEntity(id))
        val webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:$localPort").build()
        webTestClient.get().uri("/entity/${id}")
            .exchange()
            .expectStatus()
            .isOk
    }

    @Test
    fun `stress test`(): Unit = runBlocking {
        withContext(Dispatchers.IO) {
            repeat(10) {
                launch {
                    val id = UUID.randomUUID()
                    entityRepository.save(TestEntity(id))
                    val webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:$localPort").build()
                    webTestClient.get().uri("/entity/${id}")
                        .exchange()
                        .expectStatus()
                        .isOk
                }
            }
            delay(5000)
        }
    }

}