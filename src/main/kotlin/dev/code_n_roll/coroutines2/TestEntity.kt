package dev.code_n_roll.coroutines2

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class TestEntity(@Id val id: UUID)
