package com.teamnarita.armysterygamebackend.exception.user

class UserAlreadyExistException(val userId: String, msg: String): Exception(msg) {
}