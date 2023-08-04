package com.teamnarita.armysterygamebackend.exception.chapter

class UnauthorizedAccessException(val userId: String, msg: String): Exception(msg) {
}