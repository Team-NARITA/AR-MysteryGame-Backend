package com.teamnarita.armysterygamebackend.service.user

import com.teamnarita.armysterygamebackend.exception.user.UserAlreadyExistException
import com.teamnarita.armysterygamebackend.exception.user.UserNotFoundException
import com.teamnarita.armysterygamebackend.model.GameUser
import com.teamnarita.armysterygamebackend.model.GameUser.GameUserBuilder
import com.teamnarita.armysterygamebackend.repository.chapter.IChapterRepository
import com.teamnarita.armysterygamebackend.repository.coupon.ICouponRepository
import com.teamnarita.armysterygamebackend.repository.mystery.IMysteryRepository
import com.teamnarita.armysterygamebackend.repository.user.IUserRepository
import com.teamnarita.armysterygamebackend.utility.TimeUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GameUserService @Autowired constructor(
    private val userRepository: IUserRepository,
    private val mysteryRepository: IMysteryRepository,
    private val chapterRepository: IChapterRepository,
    private val couponRepository: ICouponRepository
) : IGameUserService {
    companion object {
        private val Logger = LoggerFactory.getLogger(GameUserService::class.java)
    }

    val timeUtil = TimeUtil
    private val cachedUser = HashMap<String, GameUser>()

    @Throws(UserAlreadyExistException::class)
    override fun register(userId: String, userName: String): GameUser {
        if (userRepository.exists(userId)) {
            throw UserAlreadyExistException(userId ,"userId: " + userId + "is Already Exist")
        }

        val userBuilder = GameUserBuilder(userId, userName, timeUtil.getCurrentTimeStamp(), GameUser.UserRole.USER)
        userBuilder.currentChapter = chapterRepository.getChapterList().first()
        userBuilder.solvedMystery = HashSet()
        userBuilder.clearedChapter = HashSet()
        userBuilder.usedCoupon = HashSet()
        val user = userBuilder.build()

        saveUser(user)
        cacheUser(user)
        return user
    }

    override fun userExist(userId: String): Boolean {
        if (cachedUser.containsKey(userId)) return true
        return userRepository.exists(userId)
    }

    @Throws(UserNotFoundException::class)
    override fun getUser(userId: String): GameUser {
        if (cachedUser.containsKey(userId)) return cachedUser[userId]!!

        val userDto = userRepository.find(userId)
            ?: throw UserNotFoundException(userId ,"userId: $userId is NotFound")

        val userBuilder = GameUserBuilder(userDto.userId, userDto.userName, userDto.createAt,
            GameUser.UserRole.getRoleById(userDto.roleId))
        val solvedMystery = mysteryRepository.getSolvedMystery(userId)
        val clearedChapter = chapterRepository.getClearedChapter(userId)
        val usedCoupon = couponRepository.getUsedCoupon(userId)
        val chapter = chapterRepository.getChapterList().firstOrNull { it.chapterId == userDto.currentChapterId}
        userBuilder.currentChapter = chapter
        userBuilder.solvedMystery = solvedMystery
        userBuilder.clearedChapter = clearedChapter
        userBuilder.usedCoupon = usedCoupon
        val user = userBuilder.build()

        cacheUser(user)
        return user
    }

    override fun createUnregisterUser(userId: String): GameUser {
        val userBuilder = GameUserBuilder(userId, "unregisterUser", timeUtil.getCurrentTimeStamp(), GameUser.UserRole.UNREGISTER_USER)
        userBuilder.currentChapter = chapterRepository.getChapterList().first()
        userBuilder.solvedMystery = HashSet()
        userBuilder.clearedChapter = HashSet()
        userBuilder.usedCoupon = HashSet()
        return userBuilder.build()
    }

    override fun clearCache() {
        //cachedUser.clear()
        Logger.info("Clear Cache")
    }

    private fun cacheUser(user: GameUser) {
        cachedUser[user.userId] = user
        Logger.info("Add ${user.userName} to Cache")
    }

    override fun saveUser(user: GameUser) {
        userRepository.save(user)
        Logger.info("Player ${user.userName} Saved userId: [${user.userId}]")
    }
}