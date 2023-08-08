package com.teamnarita.armysterygamebackend.model

import com.teamnarita.armysterygamebackend.model.dto.ClearedChapter
import com.teamnarita.armysterygamebackend.model.dto.SolvedMystery
import com.teamnarita.armysterygamebackend.model.dto.UsedCoupon
import org.springframework.security.core.GrantedAuthority

class GameUser private constructor(
    val userId: String,
    var userName: String,
    val createAt: Long,
    var role: UserRole,
    var currentChapter: ChapterData,
    val solvedMystery: HashSet<SolvedMystery>,
    val clearedChapter: HashSet<ClearedChapter>,
    val usedCoupon: HashSet<UsedCoupon>
) {
    fun addSolvedMystery(mystery: SolvedMystery) {
        solvedMystery.add(mystery)
    }

    fun addClearedChapter(chapter: ClearedChapter) {
        clearedChapter.add(chapter)
    }

    fun addUsedCoupon(coupon: UsedCoupon) {
        usedCoupon.add(coupon)
    }

    fun isSolvedMystery(mysteryId: String): Boolean {
        return solvedMystery.any { it.mysteryId == mysteryId}
    }

    fun isClearedChapter(chapterId: String): Boolean {
        return clearedChapter.any { it.chapterId == chapterId }
    }

    class GameUserBuilder(
        private val userId: String,
        private val userName: String,
        private val createAt: Long,
        private val role: UserRole
    ) {
        var currentChapter: ChapterData? = null
        var solvedMystery: HashSet<SolvedMystery> = hashSetOf()
        var clearedChapter: HashSet<ClearedChapter> = hashSetOf()
        var usedCoupon: HashSet<UsedCoupon> = hashSetOf()

        fun build(): GameUser {
            return GameUser(userId, userName, createAt, role, checkNotNull(currentChapter), solvedMystery, clearedChapter, usedCoupon)
        }
    }

    enum class UserRole(val roleName: String, val roleId: Int): GrantedAuthority {
        UNREGISTER_USER("UNREGISTER_USER", 1),
        USER("USER", 2),
        ADMIN("ADMIN", 3);

        companion object {
            fun getRoleById(roleId: Int): UserRole {
                return when (roleId) {
                    2 -> USER
                    3 -> ADMIN
                    else -> UNREGISTER_USER
                }
            }
        }

        override fun getAuthority(): String {
            return this.roleName
        }
    }
}