package ru.quipy.projections.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.quipy.api.project.user.ProjectCreatedEvent
import ru.quipy.api.project.user.ProjectUserAggregate
import ru.quipy.api.project.user.UserAddedToProjectEvent
import ru.quipy.api.user.UserAggregate
import ru.quipy.api.user.UserCreatedEvent
import ru.quipy.projections.TaskViewDomain
import ru.quipy.projections.UsersViewDomain
import ru.quipy.projections.repository.UserProjectionsRepository
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Component
class UserViewService(
    private val userProjectionsRepository: UserProjectionsRepository,
    private val subscriptionsManager: AggregateSubscriptionsManager
) {
    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(UserAggregate::class, "users-event-publisher-stream") {
            `when`(UserCreatedEvent::class) { event ->
                createUser(event)
            }
        }

        subscriptionsManager.createSubscriber(ProjectUserAggregate::class, "projects-users-event-publisher-stream") {
            `when`(ProjectCreatedEvent::class) { event ->
                addProjectToUser(event)
            }

            `when`(UserAddedToProjectEvent::class) { event ->
                addProjectToUser(event)
            }

        }
    }

    fun isExistsByNickname(nickname: String): Boolean {
        return userProjectionsRepository.findByNickname(nickname)
    }

    fun getUserById(id: UUID): UsersViewDomain.User {
        val user = userProjectionsRepository.findById(id)
        if (user.isPresent) {
            return user.get();
        } else {
            throw IllegalArgumentException("User with $id not found")
        }
    }

    fun getUsersById(listId: MutableList<UUID>): Iterable<UsersViewDomain.User> {
        return userProjectionsRepository.findAllById(listId)
    }

    private fun createUser(event: UserCreatedEvent) {
        val user = UsersViewDomain.User(event.userId, event.nickname)
        userProjectionsRepository.save(user)
    }

    private fun addProjectToUser(event: ProjectCreatedEvent) {
        val optionalUser = userProjectionsRepository.findById(event.creatorId)
        if (optionalUser.isPresent) {
            val user = optionalUser.get()
            user.projectsIds.add(event.projectId)
            userProjectionsRepository.save(user)
        }
    }

    private fun addProjectToUser(event: UserAddedToProjectEvent) {
        val optionalUser = userProjectionsRepository.findById(event.userId)
        if (optionalUser.isPresent) {
            val user = optionalUser.get()
            user.projectsIds.add(event.projectId)
            userProjectionsRepository.save(user)
        }
    }
}
