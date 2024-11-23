package ru.quipy.projections.service

import org.springframework.stereotype.Component
import ru.quipy.api.project.task.status.*
import ru.quipy.projections.ProjectViewDomain
import ru.quipy.projections.TaskStatusViewDomain
import ru.quipy.projections.TaskViewDomain
import ru.quipy.projections.repository.TaskProjectionsRepository
import ru.quipy.projections.repository.TaskStatusProjectionsRepository
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.UUID
import javax.annotation.PostConstruct

@Component
class TaskStatusViewService(
private val taskStatusProjectionsRepository: TaskStatusProjectionsRepository,
private val subscriptionsManager: AggregateSubscriptionsManager
) {
    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(ProjectTaskStatusAggregate::class, "tasks-statuses-event-publisher-stream") {
            `when`(TaskStatusCreatedEvent::class) { event ->
                createTaskStatus(event)
            }

            `when`(TaskStatusTitleUpdatedEvent::class) { event ->
                updateTaskStatusTitle(event)
            }

            `when`(TaskStatusColorUpdatedEvent::class) { event ->
                updateTaskStatusColor(event)
            }

            `when`(TaskStatusDeletedEvent::class) { event ->
                deleteTaskStatus(event)
            }
        }
    }

    fun getTaskStatusById(id: UUID): TaskStatusViewDomain.TaskStatus {
        val taskStatus = taskStatusProjectionsRepository.findById(id)
        if (taskStatus.isPresent) {
            return taskStatus.get();
        } else {
            throw IllegalArgumentException("Task status with $id not found")
        }
    }

    fun getTaskStatusesById(listId: MutableList<UUID>): Iterable<TaskStatusViewDomain.TaskStatus> {
        return taskStatusProjectionsRepository.findAllById(listId)
    }

    private fun createTaskStatus(event: TaskStatusCreatedEvent) {
        val task = TaskStatusViewDomain.TaskStatus(event.taskStatusId, event.title, event.color)
        taskStatusProjectionsRepository.save(task)
    }

    private fun updateTaskStatusTitle(event: TaskStatusTitleUpdatedEvent) {
        val optionalTaskStatus = taskStatusProjectionsRepository.findById(event.taskStatusId)
        if (optionalTaskStatus.isPresent) {
            val taskStatus = optionalTaskStatus.get()
            taskStatus.name = event.newTitle
            taskStatusProjectionsRepository.save(taskStatus)
        }
    }

    private fun updateTaskStatusColor(event: TaskStatusColorUpdatedEvent) {
        val optionalTaskStatus = taskStatusProjectionsRepository.findById(event.taskStatusId)
        if (optionalTaskStatus.isPresent) {
            val taskStatus = optionalTaskStatus.get()
            taskStatus.color = event.newColor
            taskStatusProjectionsRepository.save(taskStatus)
        }
    }

    private fun deleteTaskStatus(event: TaskStatusDeletedEvent) {
        taskStatusProjectionsRepository.deleteById(event.taskStatusId)
    }
}
