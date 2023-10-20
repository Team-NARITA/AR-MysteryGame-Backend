package com.teamnarita.armysterygamebackend.api

import com.teamnarita.armysterygamebackend.exception.coupon.CouponNotAvailableException
import com.teamnarita.armysterygamebackend.exception.coupon.CouponNotFoundException
import com.teamnarita.armysterygamebackend.model.UserCouponData
import com.teamnarita.armysterygamebackend.model.UserDetailsImpl
import com.teamnarita.armysterygamebackend.model.dto.UsedCoupon
import com.teamnarita.armysterygamebackend.service.coupon.ICouponService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/coupon")
class CouponController(private val couponService: ICouponService) {
    companion object {
        private val Logger = LoggerFactory.getLogger(CouponController::class.java)
    }

    @GetMapping("/{couponId}")
    fun getCouponById(@AuthenticationPrincipal principal: UserDetailsImpl, @PathVariable couponId: String): ResponseEntity<UserCouponData> {
        Logger.info("Request:GET /coupon/$couponId User: ${principal.gameUser.userName}[${principal.gameUser.userId}]")
        val coupon = couponService.getCouponById(principal.gameUser, couponId)
        return ResponseEntity(coupon, HttpStatus.OK)
    }

    @GetMapping("/")
    fun getCouponList(@AuthenticationPrincipal principal: UserDetailsImpl): ResponseEntity<List<UserCouponData>> {
        Logger.info("Request:GET /coupon/ User: ${principal.gameUser.userName}[${principal.gameUser.userId}]")
        val couponList = couponService.getUserCouponList(principal.gameUser)
        return ResponseEntity(couponList, HttpStatus.OK)
    }

    @GetMapping("/use/{couponId}")
    fun useCoupon(@AuthenticationPrincipal principal: UserDetailsImpl, @PathVariable couponId: String): ResponseEntity<UsedCoupon> {
        Logger.info("Request:GET /coupon/${couponId} User: ${principal.gameUser.userName}[${principal.gameUser.userId}]")
        val usedCoupon = couponService.useCoupon(principal.gameUser, couponId)
        return ResponseEntity(usedCoupon, HttpStatus.OK)
    }

    @ExceptionHandler(CouponNotAvailableException::class)
    fun handleException(ex: CouponNotAvailableException): ErrorResponse {
        return ErrorResponse.create(ex, HttpStatus.FORBIDDEN, "")
    }

    @ExceptionHandler(CouponNotFoundException::class)
    fun handleException(ex: CouponNotFoundException): ErrorResponse  {
        return ErrorResponse.create(ex, HttpStatus.NOT_FOUND, "")
    }
}