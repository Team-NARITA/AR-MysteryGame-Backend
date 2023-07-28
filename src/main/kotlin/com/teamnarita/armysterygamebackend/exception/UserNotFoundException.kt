package com.teamnarita.armysterygamebackend.exception

class UserNotFoundException(val userId: String, msg: String): Exception(msg) {
}