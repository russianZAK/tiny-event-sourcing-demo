package ru.quipy.projections.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import ru.quipy.projections.UsersViewDomain
import java.util.UUID

@Repository
interface UserProjectionsRepository : MongoRepository<UsersViewDomain.User, UUID> {
    fun findByNickname(nickname: String): Boolean
}
