package com.teamnarita.armysterygamebackend.repository

import com.teamnarita.armysterygamebackend.model.GameUser
import org.springframework.stereotype.Repository

@Repository
class UserRepository: IUserRepository {
    override fun find(): GameUser? {
        TODO("Not yet implemented")
    }

    override fun save(): Boolean {
        TODO("Not yet implemented")
    }

    override fun existName(): Boolean {
        TODO("Not yet implemented")
    }
}