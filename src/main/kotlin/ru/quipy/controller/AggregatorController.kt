package ru.quipy.controller

import org.springframework.web.bind.annotation.*
import ru.quipy.projections.ProjectViewDomain
import ru.quipy.projections.TaskStatusViewDomain
import ru.quipy.projections.TaskViewDomain
import ru.quipy.projections.aggregaator.AggregatorService
import ru.quipy.projections.aggregaator.TaskInfo
import java.util.*

@RestController
@RequestMapping("/aggregate")
class AggregatorController(
    val aggregatorService: AggregatorService
) {
    @GetMapping("/user-exists/{nickname}")
    fun isUserExistsByNickname(@PathVariable nickname: String): Boolean {
        return aggregatorService.isUserExistsByNickname(nickname)
    }

    @GetMapping("/project-by-user-id/{userId}")
    fun getAllProjectsByUserId(@PathVariable userId: UUID): MutableList<ProjectViewDomain.Project> {
        return aggregatorService.getAllProjectsByUserId(userId)
    }

    @GetMapping("/task-status-by-task-id/{taskId}")
    fun getTaskStatusHistoryByTaskId(@PathVariable taskId: UUID): MutableList<TaskStatusViewDomain.TaskStatus> {
        return aggregatorService.getTaskStatusHistoryByTaskId(taskId)
    }

    @GetMapping("/tasks-by-project-id/{projectId}")
    fun getAllTasksByProjectId(@PathVariable projectId: UUID): MutableList<TaskViewDomain.Task> {
        return aggregatorService.getAllTasksByProjectId(projectId)
    }

    @GetMapping("/task-details-by-id/{taskId}")
    fun getTaskDetailsByTaskId(@PathVariable taskId: UUID): TaskInfo {
        return aggregatorService.getTaskDetailsByTaskId(taskId)
    }

    @GetMapping("/user-tasks-by-id/{userId}")
    fun getUserTasksByUserId(@PathVariable userId: UUID): MutableList<TaskViewDomain.Task> {
        return aggregatorService.getUserTasksByUserId(userId)
    }
}
