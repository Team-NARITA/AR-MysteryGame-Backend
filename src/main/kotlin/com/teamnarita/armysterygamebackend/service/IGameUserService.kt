package com.teamnarita.armysterygamebackend.service

import com.teamnarita.armysterygamebackend.exception.UserAlreadyExistException
import com.teamnarita.armysterygamebackend.exception.UserNotFoundException
import com.teamnarita.armysterygamebackend.model.GameUser
import kotlin.jvm.Throws

interface IGameUserService {

    @Throws(UserAlreadyExistException::class)
    fun register(userId: String, userName: String): GameUser

    fun userExist(userId: String): Boolean

    @Throws(UserNotFoundException::class)
    fun getUser(userId: String): GameUser

    fun saveUser(user: GameUser)
}