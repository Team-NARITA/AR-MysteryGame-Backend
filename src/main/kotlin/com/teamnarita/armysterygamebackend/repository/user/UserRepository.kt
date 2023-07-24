package com.teamnarita.armysterygamebackend.repository.user

import com.teamnarita.armysterygamebackend.model.GameUser
import com.teamnarita.armysterygamebackend.model.dto.UserDTO
import org.springframework.stereotype.Repository

@Repository
class UserRepository: IUserRepository {
    override fun find(userId: String): UserDTO? {
        TODO("Not yet implemented")
    }

    override fun save(gameUser: GameUser): Boolean {
        TODO("Not yet implemented")
    }

    override fun exists(userId: String): Boolean {
        TODO("Not yet implemented")
    }
}