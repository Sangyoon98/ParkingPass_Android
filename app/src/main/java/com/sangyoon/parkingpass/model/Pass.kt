package com.sangyoon.parkingpass.model

data class Pass(
    val id: String = "",                 // Firestore 문서 id or UUID
    val timestampMillis: Long = 0,       // 통과 시간 (epoch millis, Firestore 호환)
    val direction: Direction? = null,          // IN / OUT
    val gateId: String? = null,        // 출입구 정보
    val plateNumber: String = "",           // OCR로 읽은 번호
    val confidence: Float? = null,     // 인식 신뢰도
    val imageUrl: String? = null,      // 차량 이미지 경로
    val source: Source = Source.AUTO,  // AUTO: 자동 인식, MANUAL: 수동 입력
    val carId: String? = null          // 저장 차량과 매칭된 경우 참조
)

enum class Direction { IN, OUT }
enum class Source { AUTO, MANUAL }