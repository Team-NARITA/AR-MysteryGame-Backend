package com.teamnarita.armysterygamebackend.model

class GameUser private constructor(
    val userId: String,
    val userName: String,
    val createAt: Long,
    val solvedMystery: HashSet<String>,
    val clearedChapter: HashSet<String>,
    val usedCoupon: HashSet<String>
) {
    fun addSolvedMystery(mysteryId: String) {
        solvedMystery.add(mysteryId)
    }

    fun addClearedChapter(chapterId: String) {
        clearedChapter.add(chapterId)
    }

    fun addUsedCoupon(couponId: String) {
        usedCoupon.add(couponId)
    }

    class GameUserBuilder(private val userId: String, private val userName: String, private val createAt: Long) {
        lateinit var solvedMystery: HashSet<String>
        lateinit var clearedChapter: HashSet<String>
        lateinit var usedCoupon: HashSet<String>

        fun build(): GameUser {
            return GameUser(userId, userName, createAt, solvedMystery, clearedChapter, usedCoupon)
        }
    }
}