package ru.quipy.projections.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import ru.quipy.projections.ProjectViewDomain
import java.util.*

@Repository
interface ProjectProjectionsRepository : MongoRepository<ProjectViewDomain.Project, UUID>
