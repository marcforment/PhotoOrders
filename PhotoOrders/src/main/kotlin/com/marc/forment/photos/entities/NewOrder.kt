package com.marc.forment.photos.entities

import java.time.LocalDateTime

data class NewOrder(
        val name: String,
        val surname: String,
        val email: String,
        val cellNumber: String,
        val photoType: PhotoType,
        val title: String?,
        val logisticInfo: String?,
        val dateTime: LocalDateTime?
)