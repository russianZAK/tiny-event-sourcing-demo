package ru.quipy.projections

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.quipy.api.project.task.status.*
import ru.quipy.api.project.user.ProjectCreatedEvent
import ru.quipy.api.project.user.ProjectUserAggregate
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.project.task.status.ProjectTaskStatusAggregateState
import ru.quipy.logic.project.task.status.createProjectWithTasks
import ru.quipy.saga.SagaManager
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Component
class ProjectTaskStatusEventsSubscriber (
    private val subscriptionsManager: AggregateSubscriptionsManager,
    private val projectTaskStatusEsService: EventSourcingService<UUID, ProjectTaskStatusAggregate, ProjectTaskStatusAggregateState>,
    private val sagaManager: SagaManager
) {

    val projectTaskStatusSagaName = "PROJECT_CREATED"
    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(ProjectUserAggregate::class, "project-task-status::project-user-subscriber") {
            `when`(ProjectCreatedEvent::class) { event ->
                val sagaContext = sagaManager
                    .withContextGiven(event.sagaContext)
                    .performSagaStep(projectTaskStatusSagaName, "create project with tasks")
                    .sagaContext()

                projectTaskStatusEsService.create(sagaContext) {
                    it.createProjectWithTasks(event.projectId)
                }
            }
        }
    }
}
