package com.teamnarita.armysterygamebackend.exception.user

class UserNotFoundException(val userId: String, msg: String): Exception(msg) {
}