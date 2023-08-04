package com.teamnarita.armysterygamebackend.exception

class UnauthorizedAccessException(val userId: String, msg: String): Exception(msg) {
}