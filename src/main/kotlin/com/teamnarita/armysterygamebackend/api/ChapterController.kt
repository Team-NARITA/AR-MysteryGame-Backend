package com.teamnarita.armysterygamebackend.api

import com.teamnarita.armysterygamebackend.model.ChapterData
import com.teamnarita.armysterygamebackend.model.UserDetailsImpl
import com.teamnarita.armysterygamebackend.model.dto.ClearedChapter
import com.teamnarita.armysterygamebackend.service.chapter.IChapterService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/chapter")
class ChapterController(private val chapterService: IChapterService) {
    @GetMapping("/file/{chapterId}")
    fun getChapterFile(@AuthenticationPrincipal principal: UserDetailsImpl, @PathVariable chapterId: String): String {
        val gameUser = principal.gameUser
        val chapterFile = chapterService.getChapterFileByUser(gameUser, chapterId)

        return chapterFile.bufferedReader().use { it.readText() }
    }

    @GetMapping("/info/{chapterId}")
    fun getChapterInfo(@AuthenticationPrincipal principal: UserDetailsImpl, @PathVariable chapterId: String): ResponseEntity<ChapterData> {
        val gameUser = principal.gameUser
        val chapterData = chapterService.getChapterDataByUser(gameUser, chapterId)
        return ResponseEntity(chapterData, HttpStatus.OK)
    }

    @GetMapping("/currentId")
    fun getCurrentChapter(@AuthenticationPrincipal principal: UserDetailsImpl): ResponseEntity<ChapterData> {
        val chapterData = chapterService.getCurrentChapter(principal.gameUser)
        return ResponseEntity(chapterData, HttpStatus.OK)
    }

    @PostMapping("/clear/{chapterId}")
    fun clearChapter(@AuthenticationPrincipal principal: UserDetailsImpl, @PathVariable chapterId: String): ResponseEntity<ClearedChapter> {
        val gameUser = principal.gameUser
        val clearedChapter = chapterService.clearChapter(gameUser, chapterId)
        return ResponseEntity(clearedChapter, HttpStatus.OK)
    }
}