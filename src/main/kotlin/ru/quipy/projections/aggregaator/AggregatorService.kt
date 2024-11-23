package ru.quipy.projections.aggregaator

import org.springframework.stereotype.Service
import ru.quipy.projections.ProjectViewDomain
import ru.quipy.projections.TaskStatusViewDomain
import ru.quipy.projections.TaskViewDomain
import ru.quipy.projections.UsersViewDomain
import ru.quipy.projections.service.ProjectViewService
import ru.quipy.projections.service.TaskStatusViewService
import ru.quipy.projections.service.TaskViewService
import ru.quipy.projections.service.UserViewService
import java.util.UUID

@Service
class AggregatorService (
    private val projectViewService: ProjectViewService,
    private val taskStatusViewService: TaskStatusViewService,
    private val taskViewService: TaskViewService,
    private val userViewService: UserViewService
) {

    fun isUserExistsByNickname(nickname: String): Boolean {
        return userViewService.isExistsByNickname(nickname)
    }

    fun getAllProjectsByUserId(userId: UUID): MutableList<ProjectViewDomain.Project> {
        val user = userViewService.getUserById(userId)
        return projectViewService.getProjectsById(user.projectsIds).toMutableList()
    }

    fun getTaskStatusHistoryByTaskId(taskId: UUID): MutableList<TaskStatusViewDomain.TaskStatus> {
        val task = taskViewService.getTaskById(taskId)
        return taskStatusViewService.getTaskStatusesById(task.taskStatusIds).toMutableList()
    }

    fun getAllTasksByProjectId(projectId: UUID): MutableList<TaskViewDomain.Task> {
        return taskViewService.getTasksByProjectId(projectId)
    }

    fun getTaskDetailsByTaskId(taskId: UUID): TaskInfo {
        val task = taskViewService.getTaskById(taskId)
        val taskStatuses = taskStatusViewService.getTaskStatusesById(task.taskStatusIds).toMutableList()
        val project = projectViewService.getProjectById(task.projectId)
        val assignee = userViewService.getUserById(task.assigneeId!!)
        return TaskInfo(task.id, task.title, task.description, project, assignee, taskStatuses)
    }

    fun getUserTasksByUserId(userId: UUID): MutableList<TaskViewDomain.Task> {
        return taskViewService.getTasksByAssigneeId(userId)
    }
}

data class TaskInfo(
    val id: UUID,
    var title: String,
    var description: String,
    var project: ProjectViewDomain.Project,
    var assignee: UsersViewDomain.User?,
    val taskStatuses:  MutableList<TaskStatusViewDomain.TaskStatus> = mutableListOf()
)
