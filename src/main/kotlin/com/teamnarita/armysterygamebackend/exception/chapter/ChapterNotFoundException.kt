package com.teamnarita.armysterygamebackend.exception.chapter

class ChapterNotFoundException(val chapterId: String, msg: String): Exception(msg) {
}