package ru.quipy.logic.project.user

import ru.quipy.api.project.user.ProjectUserAggregate
import ru.quipy.api.project.user.ProjectCreatedEvent
import ru.quipy.api.project.user.UserAddedToProjectEvent
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*


class ProjectUserAggregateState : AggregateState<UUID, ProjectUserAggregate> {
    private lateinit var projectId: UUID
    lateinit var title: String
    lateinit var creatorId: UUID
    var users = mutableMapOf<UUID, UserEntity>()
    var createdAt: Long = System.currentTimeMillis()
    var updatedAt: Long = System.currentTimeMillis()

    override fun getId() = projectId

    @StateTransitionFunc
    fun projectCreatedApply(event: ProjectCreatedEvent) {
        projectId = event.projectId
        title = event.title
        creatorId = event.creatorId
        users[event.creatorId] = UserEntity(event.creatorId)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun userAddedToProjectApply(event: UserAddedToProjectEvent) {
        if (users.containsKey(event.userId)) throw IllegalArgumentException("User - ${event.userId} is already exists in project - ${event.projectId}")
        users[event.userId] = UserEntity(event.userId)
        updatedAt = event.createdAt
    }
}

data class UserEntity(
    val userId: UUID
)
