package com.teamnarita.armysterygamebackend.exception.mystery

class MysteryNotFoundException(val mysteryId: String, msg: String): Exception(msg) {
}