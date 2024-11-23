package ru.quipy.projections

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.quipy.domain.Unique
import java.util.*

class UsersViewDomain {
    @Document("user-view")
    data class User(
        @Id
        override val id: UUID,
        var nickname: String,
        val projectsIds: MutableList<UUID> = mutableListOf()
    ) : Unique<UUID>
}
