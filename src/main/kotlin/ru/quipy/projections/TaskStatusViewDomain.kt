package ru.quipy.projections

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.quipy.domain.Unique
import java.util.*

class TaskStatusViewDomain {
    @Document("task-status-view")
    data class TaskStatus(
        @Id
        override val id: UUID,
        var name: String,
        var color: String
    ) : Unique<UUID>
}
