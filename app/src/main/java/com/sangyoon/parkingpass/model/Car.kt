package com.sangyoon.parkingpass.model

data class Car(
    val id: String = "",                 // Firestore 문서 id or UUID
    val plateNumber: String = "",          // 차량 번호
    val carType: String? = null,      // 차종(옵션)
    val ownerName: String? = null,    // 소유자 이름(옵션)
    val ownerPhoneNumber: String? = null, // 연락처(옵션)
    val notes: String? = null,        // 메모
    val isActive: Boolean = true,     // 차량 관리 상태
    val createdAtMillis: Long = 0,
    val updatedAtMillis: Long = 0,
)
