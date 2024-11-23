package ru.quipy.logic.project.user

import ru.quipy.api.project.user.ProjectCreatedEvent
import ru.quipy.api.project.user.UserAddedToProjectEvent
import java.util.*

fun ProjectUserAggregateState.create(id: UUID = UUID.randomUUID(), title: String, creatorId: UUID): ProjectCreatedEvent {
    if (title.length < 3) {
        throw IllegalArgumentException("Title is too small $title")
    }

    return ProjectCreatedEvent(
        projectId = id,
        title = title,
        creatorId = creatorId,
    )
}

fun ProjectUserAggregateState.addUser(projectId: UUID, userId: UUID): UserAddedToProjectEvent {
    return UserAddedToProjectEvent(
        projectId = projectId,
        userId = userId
    )
}
