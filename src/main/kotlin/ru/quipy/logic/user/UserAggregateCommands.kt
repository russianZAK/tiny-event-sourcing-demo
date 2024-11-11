package ru.quipy.logic.user

import ru.quipy.api.user.UserCreatedEvent
import java.util.*

fun UserAggregateState.createUser(id: UUID = UUID.randomUUID(), nickname: String, password: String): UserCreatedEvent {
    if (nickname.length < 3) {
        throw IllegalArgumentException("Username is too short: $nickname")
    }
    if (password.length < 8) {
        throw IllegalArgumentException("Password is too weak for user: $nickname")
    }

    return UserCreatedEvent(
        userId = id,
        nickname = nickname,
        password = password
    )
}
