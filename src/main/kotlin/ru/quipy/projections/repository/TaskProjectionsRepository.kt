package ru.quipy.projections.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import ru.quipy.projections.TaskViewDomain
import java.util.*

@Repository
interface TaskProjectionsRepository : MongoRepository<TaskViewDomain.Task, UUID> {
    fun getTasksByProjectId(projectId: UUID): MutableList<TaskViewDomain.Task>

    fun getTasksByAssigneeId(assigneeId: UUID): MutableList<TaskViewDomain.Task>
}
