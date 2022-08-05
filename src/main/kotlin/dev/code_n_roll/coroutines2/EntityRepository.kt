package dev.code_n_roll.coroutines2

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface EntityRepository: JpaRepository<TestEntity, UUID>