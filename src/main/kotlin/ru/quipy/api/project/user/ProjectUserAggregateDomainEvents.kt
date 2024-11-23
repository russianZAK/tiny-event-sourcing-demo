package ru.quipy.api.project.user

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val PROJECT_CREATED_EVENT = "PROJECT_CREATED_EVENT"
const val USER_ADDED_TO_PROJECT_EVENT = "USER_ADDED_TO_PROJECT_EVENT";

@DomainEvent(name = PROJECT_CREATED_EVENT)
class ProjectCreatedEvent(
    val projectId: UUID,
    val title: String,
    val creatorId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectUserAggregate>(
    name = PROJECT_CREATED_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = USER_ADDED_TO_PROJECT_EVENT)
class UserAddedToProjectEvent(
    val projectId: UUID,
    val userId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectUserAggregate>(
    name = USER_ADDED_TO_PROJECT_EVENT,
    createdAt = createdAt
)


