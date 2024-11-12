package ru.quipy.api.project.task.status

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val PROJECT_TASKS_CREATED_EVENT = "PROJECT_TASKS_CREATED_EVENT"

const val TASK_STATUS_CREATED_EVENT = "TASK_STATUS_CREATED_EVENT"
const val TASK_STATUS_TITLE_UPDATED_EVENT = "TASK_STATUS_TITLE_UPDATED_EVENT"
const val TASK_STATUS_COLOR_UPDATED_EVENT = "TASK_STATUS_COLOR_UPDATED_EVENT"
const val TASK_STATUS_DELETED_EVENT = "TASK_STATUS_DELETED_EVENT"

const val TASK_CREATED_EVENT = "TASK_CREATED_EVENT"
const val USER_ASSIGNED_TO_TASK_EVENT = "USER_ASSIGNED_TO_TASK_EVENT"
const val TASK_STATUS_UPDATED_EVENT = "TASK_STATUS_UPDATED_EVENT"
const val TASK_TITLE_UPDATED_EVENT = "TASK_TITLE_UPDATED_EVENT"
const val TASK_DESCRIPTION_UPDATED_EVENT = "TASK_DESCRIPTION_UPDATED_EVENT"

@DomainEvent(name = PROJECT_TASKS_CREATED_EVENT)
class ProjectTasksCreatedEvent(
    val projectId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectTaskStatusAggregate>(
    name = PROJECT_TASKS_CREATED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = TASK_CREATED_EVENT)
class TaskCreatedEvent(
    val taskId: UUID,
    val projectId: UUID,
    val title: String,
    val description: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectTaskStatusAggregate>(
    name = TASK_CREATED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = USER_ASSIGNED_TO_TASK_EVENT)
class UserAssignedToTaskEvent(
    val projectId: UUID,
    val taskId: UUID,
    val assigneeId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectTaskStatusAggregate>(
    name = USER_ASSIGNED_TO_TASK_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = TASK_STATUS_UPDATED_EVENT)
class TaskStatusUpdatedEvent(
    val projectId: UUID,
    val taskId: UUID,
    val newTaskStatusId: UUID,
    updatedAt: Long = System.currentTimeMillis()
) : Event<ProjectTaskStatusAggregate>(
    name = TASK_STATUS_UPDATED_EVENT,
    createdAt = updatedAt
)

@DomainEvent(name = TASK_TITLE_UPDATED_EVENT)
class TaskTitleUpdatedEvent(
    val projectId: UUID,
    val taskId: UUID,
    val newTitle: String,
    updatedAt: Long = System.currentTimeMillis()
) : Event<ProjectTaskStatusAggregate>(
    name = TASK_TITLE_UPDATED_EVENT,
    createdAt = updatedAt
)

@DomainEvent(name = TASK_DESCRIPTION_UPDATED_EVENT)
class TaskDescriptionUpdatedEvent(
    val projectId: UUID,
    val taskId: UUID,
    val newDescription: String,
    updatedAt: Long = System.currentTimeMillis()
) : Event<ProjectTaskStatusAggregate>(
    name = TASK_DESCRIPTION_UPDATED_EVENT,
    createdAt = updatedAt
)

@DomainEvent(name = TASK_STATUS_CREATED_EVENT)
class TaskStatusCreatedEvent(
    val projectId: UUID,
    val taskStatusId: UUID,
    val title: String,
    val color: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectTaskStatusAggregate>(
    name = TASK_STATUS_CREATED_EVENT,
    createdAt = createdAt,
)
@DomainEvent(name = TASK_STATUS_TITLE_UPDATED_EVENT)
class TaskStatusTitleUpdatedEvent(
    val projectId: UUID,
    val taskStatusId: UUID,
    val newTitle: String,
    createdAt: Long = System.currentTimeMillis()
) : Event<ProjectTaskStatusAggregate>(
    name = TASK_STATUS_TITLE_UPDATED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = TASK_STATUS_COLOR_UPDATED_EVENT)
class TaskStatusColorUpdatedEvent(
    val projectId: UUID,
    val taskStatusId: UUID,
    val newColor: String,
    createdAt: Long = System.currentTimeMillis()
) : Event<ProjectTaskStatusAggregate>(
    name = TASK_STATUS_COLOR_UPDATED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = TASK_STATUS_DELETED_EVENT)
class TaskStatusDeletedEvent(
    val taskStatusId: UUID,
    val projectId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectTaskStatusAggregate>(
    name = TASK_STATUS_DELETED_EVENT,
    createdAt = createdAt,
)
