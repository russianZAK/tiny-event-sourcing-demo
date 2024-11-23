package ru.quipy.projections.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import ru.quipy.projections.TaskStatusViewDomain
import java.util.*

@Repository
interface TaskStatusProjectionsRepository : MongoRepository<TaskStatusViewDomain.TaskStatus, UUID>
