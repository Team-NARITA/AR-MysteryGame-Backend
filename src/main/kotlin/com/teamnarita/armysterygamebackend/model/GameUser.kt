package com.teamnarita.armysterygamebackend.model

import org.springframework.security.core.GrantedAuthority

class GameUser private constructor(
    val userId: String,
    val userName: String,
    val createAt: Long,
    val role: UserRole,
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

    class GameUserBuilder(
        private val userId: String,
        private val userName: String,
        private val createAt: Long,
        private val role: UserRole
    ) {
        var solvedMystery: HashSet<String> = hashSetOf()
        var clearedChapter: HashSet<String> = hashSetOf()
        var usedCoupon: HashSet<String> = hashSetOf()

        fun build(): GameUser {
            return GameUser(userId, userName, createAt, role, solvedMystery, clearedChapter, usedCoupon)
        }
    }

    enum class UserRole(val roleName: String): GrantedAuthority {
        UNREGISTER_USER("UNREGISTER_USER"),
        USER("USER"),
        ADMIN("ADMIN"),
        NO_ROLE("NO_ROLE");

        companion object {
            fun getRoleById(roleId: Int): UserRole {
                return when (roleId) {
                    1 -> UNREGISTER_USER
                    2 -> USER
                    3 -> ADMIN
                    else -> NO_ROLE
                }
            }
        }

        override fun getAuthority(): String {
            return this.roleName
        }
    }
}