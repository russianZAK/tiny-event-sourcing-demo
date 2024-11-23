package ru.quipy.logic.project.task.status

import ru.quipy.api.project.task.status.*
import ru.quipy.api.user.UserCreatedEvent
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.logic.project.user.UserEntity
import java.util.*

class ProjectTaskStatusAggregateState : AggregateState<UUID, ProjectTaskStatusAggregate> {
    private lateinit var projectId: UUID
    var defaultProjectTaskStatus = TaskStatus(UUID.randomUUID(), "TO DO", "WHITE")
    var projectTasks = mutableMapOf<UUID, Task>()
    var projectTaskStatuses = mutableMapOf<UUID, TaskStatus>()
    var createdAt: Long = System.currentTimeMillis()
    var updatedAt: Long = System.currentTimeMillis()

    override fun getId() = projectId

    @StateTransitionFunc
    fun projectCreatedWithTasksApply(event: ProjectTasksCreatedEvent) {
        projectId = event.projectId
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun taskStatusCreatedApply(event: TaskStatusCreatedEvent) {
        projectTaskStatuses[event.taskStatusId] = TaskStatus(event.taskStatusId, event.title, event.color)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun taskStatusTitleUpdatedApply(event: TaskStatusTitleUpdatedEvent) {
        val oldTaskStatus = projectTaskStatuses[event.taskStatusId] ?: throw IllegalArgumentException("Task status not found: ${event.taskStatusId}")

        val updatedTaskStatus = oldTaskStatus.copy(title = event.newTitle)
        projectTaskStatuses[event.taskStatusId] = updatedTaskStatus

        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun taskStatusColorUpdatedApply(event: TaskStatusColorUpdatedEvent) {
        val oldTaskStatus = projectTaskStatuses[event.taskStatusId] ?: throw IllegalArgumentException("Task status not found: ${event.taskStatusId}")

        val updatedTaskStatus = oldTaskStatus.copy(color = event.newColor)
        projectTaskStatuses[event.taskStatusId] = updatedTaskStatus

        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun taskStatusDeletedApply(event: TaskStatusDeletedEvent) {
        projectTaskStatuses.remove(event.taskStatusId)

        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun taskCreatedApply(event: TaskCreatedEvent) {
        projectTasks[event.taskId] = Task(event.taskId, event.title, event.description, defaultProjectTaskStatus, null)

        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun userAssignedToTaskApply(event: UserAssignedToTaskEvent) {
        val oldTask = projectTasks[event.taskId] ?: throw IllegalArgumentException("Task not found: ${event.taskId}")

        val updatedTask = oldTask.copy(assigneeId = event.assigneeId)
        projectTasks[event.taskId] = updatedTask

        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun updateTaskStatusApply(event: TaskStatusUpdatedEvent) {
        val oldTask = projectTasks[event.taskId] ?: throw IllegalArgumentException("Task not found: ${event.taskId}")
        val taskStatus = projectTaskStatuses[event.newTaskStatusId] ?: throw IllegalArgumentException("Task status not found: ${event.newTaskStatusId}")
        val updatedTask = oldTask.copy(status = taskStatus)
        projectTasks[event.taskId] = updatedTask

        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun updateTaskTitleApply(event: TaskTitleUpdatedEvent) {
        val oldTask = projectTasks[event.taskId] ?: throw IllegalArgumentException("Task not found: ${event.taskId}")
        val updatedTask = oldTask.copy(title = event.newTitle)
        projectTasks[event.taskId] = updatedTask

        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun updateTaskDescriptionApply(event: TaskDescriptionUpdatedEvent) {
        val oldTask = projectTasks[event.taskId] ?: throw IllegalArgumentException("Task not found: ${event.taskId}")
        val updatedTask = oldTask.copy(description = event.newDescription)
        projectTasks[event.taskId] = updatedTask

        updatedAt = event.createdAt
    }

}

data class Task(
    val taskId: UUID,
    val title: String,
    val description: String,
    val status: TaskStatus,
    val assigneeId: UUID ?
)

data class TaskStatus(
    val statusId: UUID,
    var title: String,
    val color: String
)
