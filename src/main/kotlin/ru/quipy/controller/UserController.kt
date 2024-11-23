package ru.quipy.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.quipy.api.user.UserAggregate
import ru.quipy.api.user.UserCreatedEvent
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.user.UserAggregateState
import ru.quipy.logic.user.createUser
import java.util.*

@RestController
@RequestMapping("/users")
class UserController(
    val userEsService: EventSourcingService<UUID, UserAggregate, UserAggregateState>
) {
    @PostMapping("/create-user")
    fun createUser(@RequestBody request: CreateUserDto) : UserCreatedEvent {
        return userEsService.create { it.createUser(nickname = request.nickname, password = request.password) }
    }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: UUID) : UserAggregateState? {
        return userEsService.getState(userId)
    }
}

data class CreateUserDto(
    val nickname: String,
    val password: String
)
