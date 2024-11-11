package ru.quipy.logic.project.task.status

import ru.quipy.api.project.task.status.*
import java.util.*

fun ProjectTaskStatusAggregateState.createProjectWithTasks(projectId: UUID): ProjectTasksCreatedEvent {
    return ProjectTasksCreatedEvent(
       projectId = projectId
    )
}

fun ProjectTaskStatusAggregateState.createTaskStatus(id: UUID = UUID.randomUUID(), projectId: UUID, title: String, color: String): TaskStatusCreatedEvent {
    return TaskStatusCreatedEvent(
        taskStatusId = id,
        projectId = projectId,
        title = title,
        color = color
    )
}

fun ProjectTaskStatusAggregateState.updateTaskStatusTitle(projectId: UUID, taskStatusId: UUID, newTitle: String): TaskStatusTitleUpdatedEvent {
    return TaskStatusTitleUpdatedEvent(
        projectId = projectId,
        taskStatusId = taskStatusId,
        newTitle = newTitle
    )
}

fun ProjectTaskStatusAggregateState.updateTaskStatusColor(projectId: UUID, taskStatusId: UUID, newColor: String): TaskStatusColorUpdatedEvent {
    return TaskStatusColorUpdatedEvent(
        projectId = projectId,
        taskStatusId = taskStatusId,
        newColor = newColor
    )
}

fun ProjectTaskStatusAggregateState.deleteTaskStatus(taskStatusId: UUID, projectId: UUID): TaskStatusDeletedEvent {
    return TaskStatusDeletedEvent(
        taskStatusId = taskStatusId,
        projectId = projectId
    )
}

fun ProjectTaskStatusAggregateState.createTask(id: UUID = UUID.randomUUID(), projectId: UUID, title: String, description: String): TaskCreatedEvent {
    return TaskCreatedEvent(
        taskId = id,
        projectId = projectId,
        title = title,
        description = description,
    )
}

fun ProjectTaskStatusAggregateState.assignUser(projectId: UUID, taskID: UUID, assigneeId: UUID): UserAssignedToTaskEvent {
    return UserAssignedToTaskEvent(
        projectId = projectId,
        taskId = taskID,
        assigneeId = assigneeId
    )
}

fun ProjectTaskStatusAggregateState.updateTaskStatus(projectId: UUID, taskId: UUID, statusId: UUID): TaskStatusUpdatedEvent {
    return TaskStatusUpdatedEvent(
        projectId = projectId,
        taskId = taskId,
        newTaskStatusId = statusId
    )
}

fun ProjectTaskStatusAggregateState.updateTaskTitle(projectId: UUID, taskId: UUID, title: String): TaskTitleUpdatedEvent {
    return TaskTitleUpdatedEvent(
        projectId = projectId,
        taskId = taskId,
        newTitle = title
    )
}

fun ProjectTaskStatusAggregateState.updateTaskDescription(projectId: UUID, taskId: UUID, description: String): TaskDescriptionUpdatedEvent {
    return TaskDescriptionUpdatedEvent(
        projectId = projectId,
        taskId = taskId,
        newDescription = description
    )
}
