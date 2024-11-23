package ru.quipy.controller

import org.springframework.web.bind.annotation.*
import ru.quipy.api.project.task.status.*
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.project.task.status.*
import ru.quipy.logic.project.user.ProjectUserAggregateState
import java.util.*

@RestController
@RequestMapping("/project-task-status")
class ProjectTaskStatusController(
    val projectTaskStatusEsService: EventSourcingService<UUID, ProjectTaskStatusAggregate, ProjectTaskStatusAggregateState>
) {
    @PostMapping("/create-task/{projectId}")
    fun createTask(@PathVariable projectId: UUID, @RequestBody request: CreateTaskDto) : TaskCreatedEvent {
        return projectTaskStatusEsService.update(projectId) { it.createTask(projectId = projectId, title = request.title, description = request.description) }
    }

    @PostMapping("/assign-user-to-task/{projectId}")
    fun addUserToProject(@PathVariable projectId: UUID, @RequestBody request: UserAssignDto) : UserAssignedToTaskEvent {
        return projectTaskStatusEsService.update(projectId) {it.assignUser(projectId, request.taskId, request.assigneeId)}
    }

    @PostMapping("/update-task-status/{projectId}")
    fun updateTaskStatus(@PathVariable projectId: UUID, @RequestBody request: TaskStatusUpdateDto) : TaskStatusUpdatedEvent {
        return projectTaskStatusEsService.update(projectId) {it.updateTaskStatus(projectId, request.taskId, request.newTaskStatusId)}
    }

    @PostMapping("/update-task-title/{projectId}")
    fun updateTaskTitle(@PathVariable projectId: UUID, @RequestBody request: TaskTitleUpdateDto) : TaskTitleUpdatedEvent {
        return projectTaskStatusEsService.update(projectId) {it.updateTaskTitle(projectId, request.taskId, request.newTitle)}
    }

    @PostMapping("/update-task-description/{projectId}")
    fun updateTaskDescription(@PathVariable projectId: UUID, @RequestBody request: TaskDescriptionUpdateDto) : TaskDescriptionUpdatedEvent {
        return projectTaskStatusEsService.update(projectId) {it.updateTaskDescription(projectId, request.taskId, request.newDescription)}
    }

    @PostMapping("/create-task-status/{projectId}")
    fun createTaskStatus(@PathVariable projectId: UUID, @RequestBody request: TaskStatusCreatedDto) : TaskStatusCreatedEvent {
        return projectTaskStatusEsService.update(projectId) {it.createTaskStatus(projectId = projectId, title = request.title, color = request.color)}
    }

    @PostMapping("/update-task-status-title/{projectId}")
    fun updateTaskStatusTitle(@PathVariable projectId: UUID, @RequestBody request: TaskStatusTitleUpdateDto) : TaskStatusTitleUpdatedEvent {
        return projectTaskStatusEsService.update(projectId) {it.updateTaskStatusTitle(projectId = projectId, taskStatusId = request.taskStatusId, newTitle = request.newTitle)}
    }

    @PostMapping("/update-task-status-color/{projectId}")
    fun updateTaskStatusColor(@PathVariable projectId: UUID, @RequestBody request: TaskStatusColorUpdateDto) : TaskStatusColorUpdatedEvent {
        return projectTaskStatusEsService.update(projectId) {it.updateTaskStatusColor(projectId = projectId, taskStatusId = request.taskStatusId, newColor = request.newColor)}
    }

    @PostMapping("/delete-task-status/{projectId}")
    fun deleteTaskStatus(@PathVariable projectId: UUID, @RequestBody request: TaskStatusDeleteDto) : TaskStatusDeletedEvent {
        return projectTaskStatusEsService.update(projectId) {it.deleteTaskStatus(projectId = projectId, taskStatusId = request.taskStatusId)}
    }

    @GetMapping("/{projectId}")
    fun getProject(@PathVariable projectId: UUID) : ProjectTaskStatusAggregateState? {
        return projectTaskStatusEsService.getState(projectId)
    }
}

data class CreateTaskDto(
    val title: String,
    val description: String
)

data class UserAssignDto(
    val taskId: UUID,
    val assigneeId: UUID
)

data class TaskStatusUpdateDto(
    val taskId: UUID,
    val newTaskStatusId: UUID
)

data class TaskTitleUpdateDto(
    val taskId: UUID,
    val newTitle: String
)

data class TaskDescriptionUpdateDto(
    val taskId: UUID,
    val newDescription: String
)

data class TaskStatusCreatedDto(
    val title: String,
    val color: String
)

data class TaskStatusTitleUpdateDto(
    val taskStatusId: UUID,
    val newTitle: String
)

data class TaskStatusColorUpdateDto(
    val taskStatusId: UUID,
    val newColor: String
)

data class TaskStatusDeleteDto(
    val taskStatusId: UUID
)
