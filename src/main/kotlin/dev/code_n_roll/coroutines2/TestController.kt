package dev.code_n_roll.coroutines2

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class TestController(private val service: TestService) {

    @GetMapping(path = ["/entity/{id}"])
    suspend fun getEntity(@PathVariable id: UUID) = service.findById(id)

}