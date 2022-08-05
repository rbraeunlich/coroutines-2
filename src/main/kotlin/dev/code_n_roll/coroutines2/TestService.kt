package dev.code_n_roll.coroutines2

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TestService(private val repository: EntityRepository) {

    private val log = KotlinLogging.logger { }

    suspend fun findById(id: UUID): TestEntity? {
        log.info { "Hello from findById outside context" }
        return withContext(Dispatchers.IO) {
            log.info { "Hello from findById inside context" }
            repository.findById(id)
        }.orElse(null)
    }
}