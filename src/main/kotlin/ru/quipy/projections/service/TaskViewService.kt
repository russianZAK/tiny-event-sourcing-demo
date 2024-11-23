package ru.quipy.projections.service

import org.springframework.stereotype.Component
import ru.quipy.api.project.task.status.*
import ru.quipy.projections.ProjectViewDomain
import ru.quipy.projections.TaskStatusViewDomain
import ru.quipy.projections.TaskViewDomain
import ru.quipy.projections.repository.TaskProjectionsRepository
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Component
class TaskViewService (
    private val taskProjectionsRepository: TaskProjectionsRepository,
    private val subscriptionsManager: AggregateSubscriptionsManager
) {
    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(ProjectTaskStatusAggregate::class, "tasks-event-publisher-stream") {
            `when`(TaskCreatedEvent::class) { event ->
                createTask(event)
            }

            `when`(UserAssignedToTaskEvent::class) { event ->
                assignUserToTask(event)
            }

            `when`(TaskStatusUpdatedEvent::class) { event ->
                updateTaskStatus(event)
            }

            `when`(TaskTitleUpdatedEvent::class) { event ->
                updateTaskTitle(event)
            }

            `when`(TaskDescriptionUpdatedEvent::class) { event ->
                updateTaskDescription(event)
            }
        }
    }

    fun getTasksByAssigneeId(assigneeId: UUID): MutableList<TaskViewDomain.Task> {
        return taskProjectionsRepository.getTasksByAssigneeId(assigneeId)
    }

    fun getTasksByProjectId(projectId: UUID): MutableList<TaskViewDomain.Task> {
        return taskProjectionsRepository.getTasksByProjectId(projectId)
    }

    fun getTaskById(id: UUID): TaskViewDomain.Task {
        val task = taskProjectionsRepository.findById(id)
        if (task.isPresent) {
            return task.get();
        } else {
            throw IllegalArgumentException("Task with $id not found")
        }
    }

    fun getTasksById(listId: MutableList<UUID>): Iterable<TaskViewDomain.Task> {
        return taskProjectionsRepository.findAllById(listId)
    }

    private fun createTask(event: TaskCreatedEvent) {
        val task = TaskViewDomain.Task(event.taskId, event.title, event.description, event.projectId, null)
        taskProjectionsRepository.save(task)
    }

    private fun assignUserToTask(event: UserAssignedToTaskEvent) {
        val optionalTask = taskProjectionsRepository.findById(event.taskId)
        if (optionalTask.isPresent) {
            val task = optionalTask.get()
            task.assigneeId = event.assigneeId
            taskProjectionsRepository.save(task)
        }
    }

    private fun updateTaskStatus(event: TaskStatusUpdatedEvent) {
        val optionalTask = taskProjectionsRepository.findById(event.taskId)
        if (optionalTask.isPresent) {
            val task = optionalTask.get()
            task.taskStatusIds.add(event.newTaskStatusId)
            taskProjectionsRepository.save(task)
        }
    }

    private fun updateTaskTitle(event: TaskTitleUpdatedEvent) {
        val optionalTask = taskProjectionsRepository.findById(event.taskId)
        if (optionalTask.isPresent) {
            val task = optionalTask.get()
            task.title = event.newTitle
            taskProjectionsRepository.save(task)
        }
    }

    private fun updateTaskDescription(event: TaskDescriptionUpdatedEvent) {
        val optionalTask = taskProjectionsRepository.findById(event.taskId)
        if (optionalTask.isPresent) {
            val task = optionalTask.get()
            task.description = event.newDescription
            taskProjectionsRepository.save(task)
        }
    }
}
