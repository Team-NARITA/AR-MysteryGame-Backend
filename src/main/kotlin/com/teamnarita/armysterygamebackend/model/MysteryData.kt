package com.teamnarita.armysterygamebackend.model

data class MysteryData(
    val mysteryId: String,
    val mysteryText: String,
    val mysteryDescription: String,
    val answers: List<String>
)