package ru.quipy.config

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.quipy.api.project.task.status.ProjectTaskStatusAggregate
import ru.quipy.api.project.user.ProjectUserAggregate
import ru.quipy.api.user.UserAggregate
import ru.quipy.core.EventSourcingServiceFactory
import ru.quipy.logic.project.task.status.ProjectTaskStatusAggregateState
import ru.quipy.logic.project.user.ProjectUserAggregateState
import ru.quipy.logic.user.UserAggregateState
import java.util.*

/**
 * This files contains some configurations that you might want to have in your project. Some configurations are
 * made in for the sake of demonstration and not required for the library functioning. Usually you can have even
 * more minimalistic config
 *
 * Take into consideration that we autoscan files searching for Aggregates, Events and StateTransition functions.
 * Autoscan enabled via [event.sourcing.auto-scan-enabled] property.
 *
 * But you can always disable it and register all the classes manually like this
 * ```
 * @Autowired
 * private lateinit var aggregateRegistry: AggregateRegistry
 *
 * aggregateRegistry.register(ProjectAggregate::class, ProjectAggregateState::class) {
 *     registerStateTransition(TagCreatedEvent::class, ProjectAggregateState::tagCreatedApply)
 *     registerStateTransition(TaskCreatedEvent::class, ProjectAggregateState::taskCreatedApply)
 *     registerStateTransition(TagAssignedToTaskEvent::class, ProjectAggregateState::tagAssignedApply)
 * }
 * ```
 */
@Configuration
class EventSourcingLibConfiguration {

    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory


    /**
     * Use this object to create/update the aggregate
     */
    @Bean
    fun projectUserEsService() = eventSourcingServiceFactory.create<UUID, ProjectUserAggregate, ProjectUserAggregateState>()

    @Bean
    fun projectTaskStatusEsService() = eventSourcingServiceFactory.create<UUID, ProjectTaskStatusAggregate, ProjectTaskStatusAggregateState>()

    @Bean
    fun userEsService() = eventSourcingServiceFactory.create<UUID, UserAggregate, UserAggregateState>()

}
