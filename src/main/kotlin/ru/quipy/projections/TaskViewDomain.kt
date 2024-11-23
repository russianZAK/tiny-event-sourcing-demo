package ru.quipy.projections

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.quipy.domain.Unique
import java.util.*

class TaskViewDomain {
    @Document("task-view")
    data class Task(
        @Id
        override val id: UUID,
        var title: String,
        var description: String,
        var projectId: UUID,
        var assigneeId: UUID?,
        val taskStatusIds:  MutableList<UUID> = mutableListOf()
    ) : Unique<UUID>
}
