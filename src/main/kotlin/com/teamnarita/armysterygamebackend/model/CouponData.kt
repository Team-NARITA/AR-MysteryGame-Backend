package com.teamnarita.armysterygamebackend.model

data class CouponData(
    val couponId: String,
    val couponName: String,
    val storeName: String,
    val storePlace: String,
    val discountItem: String,
    val originalPrice: Int,
    val discountedPrice: Int,
    val whenChapter: String
)