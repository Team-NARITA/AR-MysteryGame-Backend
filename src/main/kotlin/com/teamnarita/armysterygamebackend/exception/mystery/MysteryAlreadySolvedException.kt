package com.teamnarita.armysterygamebackend.exception.mystery

class MysteryAlreadySolvedException(val userId: String, msg: String): Exception(msg)