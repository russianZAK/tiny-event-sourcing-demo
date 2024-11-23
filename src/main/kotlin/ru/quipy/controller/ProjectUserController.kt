package ru.quipy.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.quipy.api.project.task.status.ProjectTaskStatusAggregate
import ru.quipy.api.project.user.ProjectCreatedEvent
import ru.quipy.api.project.user.UserAddedToProjectEvent
import ru.quipy.api.project.user.ProjectUserAggregate
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.project.task.status.ProjectTaskStatusAggregateState
import ru.quipy.logic.project.user.ProjectUserAggregateState
import ru.quipy.logic.project.user.addUser
import ru.quipy.logic.project.user.create
import ru.quipy.saga.SagaManager
import java.util.*

@RestController
@RequestMapping("/users-project")
class ProjectUserController(
    val projectUserEsService: EventSourcingService<UUID, ProjectUserAggregate, ProjectUserAggregateState>
    ) {
    @PostMapping("/create-project")
    fun createProject(@RequestBody request: CreateProjectDto) : ProjectCreatedEvent {
        return projectUserEsService.create { it.create(title = request.title, creatorId = request.creatorId) }
    }

    @PostMapping("/add-user-to-project/{projectId}")
    fun addUserToProject(@PathVariable projectId: UUID, @RequestBody request: UserAddToProjectDto) : UserAddedToProjectEvent {
        return projectUserEsService.update(projectId) {it.addUser(projectId, request.userId)}
    }
    @GetMapping("/{projectId}")
    fun getProject(@PathVariable projectId: UUID) : ProjectUserAggregateState? {
        return projectUserEsService.getState(projectId)
    }
}

data class CreateProjectDto(
    val title: String,
    val creatorId: UUID
)

data class UserAddToProjectDto(
    val userId: UUID
)
