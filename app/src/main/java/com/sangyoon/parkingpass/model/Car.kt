package com.sangyoon.parkingpass.model

import java.time.LocalDateTime

data class Car(
    val id: Int,
    val number: String,
    val carType: String,
    val ownerName: String,
    val ownerPhoneNumber: String,
    val inDate: LocalDateTime? = null,
    val outDate: LocalDateTime? = null
)
