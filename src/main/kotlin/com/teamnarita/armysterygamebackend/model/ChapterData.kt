package com.teamnarita.armysterygamebackend.model

data class ChapterData(
    val chapterId: String,
    val chapterName: String,
    val chapterDescription: String,
    val belongMysteries: List<String>,
    val chapterFileURL: String
)