package ru.quipy.projections.service

import org.springframework.stereotype.Component
import ru.quipy.api.project.user.ProjectCreatedEvent
import ru.quipy.api.project.user.ProjectUserAggregate
import ru.quipy.projections.ProjectViewDomain
import ru.quipy.projections.repository.ProjectProjectionsRepository
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Component
class ProjectViewService(
    private val projectProjectionsRepository: ProjectProjectionsRepository,
    private val subscriptionsManager: AggregateSubscriptionsManager
) {
    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(ProjectUserAggregate::class, "projects-event-publisher-stream") {
            `when`(ProjectCreatedEvent::class) { event ->
                createProject(event)
            }
        }
    }

    fun getProjectById(id: UUID): ProjectViewDomain.Project {
        val project = projectProjectionsRepository.findById(id)
        if (project.isPresent) {
            return project.get();
        } else {
            throw IllegalArgumentException("Project with $id not found")
        }
    }

    fun getProjectsById(listId: MutableList<UUID>): Iterable<ProjectViewDomain.Project> {
        return projectProjectionsRepository.findAllById(listId)
    }

    private fun createProject(event: ProjectCreatedEvent) {
        val project = ProjectViewDomain.Project(event.projectId, event.title)
        projectProjectionsRepository.save(project)
    }
}
