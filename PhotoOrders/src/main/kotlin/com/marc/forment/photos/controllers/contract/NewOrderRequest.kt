package com.marc.forment.photos.controllers.contract

import com.marc.forment.photos.entities.NewOrder
import com.marc.forment.photos.entities.PhotoType
import java.util.*

data class NewOrderRequest(
        val name: String,
        val surname: String,
        val email: String,
        val cellNumber: String,
        val photoType: PhotoType,
        val title: String?,
        val logisticInfo: String?,
        val dateTime: Date?
) {
    fun toNewOrder() : NewOrder =
            NewOrder(
                    name = this.name,
                    surname = this.surname,
                    email = this.email,
                    cellNumber = this.cellNumber,
                    photoType = this.photoType,
                    title = this.title,
                    logisticInfo = this.logisticInfo,
                    dateTime = this.dateTime
            )

}

