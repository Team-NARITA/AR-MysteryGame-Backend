package com.teamnarita.armysterygamebackend.api

import com.teamnarita.armysterygamebackend.exception.UnauthorizedAccessException
import com.teamnarita.armysterygamebackend.exception.chapter.ChapterNotFoundException
import com.teamnarita.armysterygamebackend.exception.chapter.ClearJudgmentException
import com.teamnarita.armysterygamebackend.model.ChapterData
import com.teamnarita.armysterygamebackend.model.UserDetailsImpl
import com.teamnarita.armysterygamebackend.model.dto.ClearedChapter
import com.teamnarita.armysterygamebackend.service.chapter.IChapterService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/chapter")
class ChapterController(private val chapterService: IChapterService) {
    companion object {
        private val Logger = LoggerFactory.getLogger(ChapterController::class.java)
    }

    @GetMapping("/file/{chapterId}", produces = ["application/json"])
    fun getChapterFile(@AuthenticationPrincipal principal: UserDetailsImpl, @PathVariable chapterId: String): ResponseEntity<String> {
        Logger.info("Request:GET /chapter/${chapterId} User: ${principal.gameUser.userName}[${principal.gameUser.userId}]")
        val gameUser = principal.gameUser
        val chapterFile = chapterService.getChapterFileByUser(gameUser, chapterId)

        return ResponseEntity(chapterFile.bufferedReader().use { it.readText() }, HttpStatus.OK)
    }

    @GetMapping("/info/{chapterId}")
    fun getChapterInfo(@AuthenticationPrincipal principal: UserDetailsImpl, @PathVariable chapterId: String): ResponseEntity<ChapterData> {
        Logger.info("Request:GET /chapter/info/${chapterId} User: ${principal.gameUser.userName}[${principal.gameUser.userId}]")
        val gameUser = principal.gameUser
        val chapterData = chapterService.getChapterDataByUser(gameUser, chapterId)
        return ResponseEntity(chapterData, HttpStatus.OK)
    }

    @GetMapping("/currentId")
    fun getCurrentChapter(@AuthenticationPrincipal principal: UserDetailsImpl): ResponseEntity<ChapterData> {
        Logger.info("Request:GET /chapter/currentId User: ${principal.gameUser.userName}[${principal.gameUser.userId}]")
        val chapterData = chapterService.getCurrentChapter(principal.gameUser)
        return ResponseEntity(chapterData, HttpStatus.OK)
    }

    @GetMapping("/authorize")
    fun getAuthorizeChapters(@AuthenticationPrincipal principal: UserDetailsImpl): ResponseEntity<List<ChapterData>> {
        Logger.info("Request:GET /chapter/authorize User: ${principal.gameUser.userName}[${principal.gameUser.userId}]")
        val chapters = chapterService.getAuthorizedChapterByUser(principal.gameUser)
        return ResponseEntity(chapters, HttpStatus.OK)
    }

    @PostMapping("/clear/{chapterId}")
    fun clearChapter(@AuthenticationPrincipal principal: UserDetailsImpl, @PathVariable chapterId: String): ResponseEntity<ClearedChapter> {
        Logger.info("Request:POST /chapter/clear/${chapterId} User: ${principal.gameUser.userName}[${principal.gameUser.userId}]")
        val gameUser = principal.gameUser
        val clearedChapter = chapterService.clearChapter(gameUser, chapterId)
        return ResponseEntity(clearedChapter, HttpStatus.OK)
    }

    @ExceptionHandler(UnauthorizedAccessException::class)
    fun handleException(ex: UnauthorizedAccessException): ErrorResponse {
        return ErrorResponse.create(ex, HttpStatus.FORBIDDEN, "このチャプターにはアクセスできません")
    }

    @ExceptionHandler(ChapterNotFoundException::class)
    fun handleException(ex: ChapterNotFoundException): ErrorResponse {
        return ErrorResponse.create(ex, HttpStatus.NOT_FOUND, "chapterId:${ex.chapterId} が見つかりませんでした")
    }

    @ExceptionHandler(ClearJudgmentException::class)
    fun handleException(ex: ClearJudgmentException): ErrorResponse {
        return ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, "")
    }
}