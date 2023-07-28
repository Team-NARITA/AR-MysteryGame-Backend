package com.teamnarita.armysterygamebackend.exception

class UserAlreadyExistException(val userId: String, msg: String): Exception(msg) {
}