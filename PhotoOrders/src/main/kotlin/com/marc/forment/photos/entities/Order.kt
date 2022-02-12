package com.marc.forment.photos.entities

import java.util.*
import javax.persistence.*

enum class PhotoType {
    REAL_STATE,
    FOOD,
    EVENTS
}

enum class OrderState {
    UNSCHEDULED,
    PENDING,
    ASSIGNED,
    UPLOADED,
    COMPLETED,
    CANCELED
}

@Entity
@Table(name = "orders")
data class Order(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String,
    val surname: String,
    val email: String,
    val cellNumber: String,
    val photoType: PhotoType,
    val title: String?,
    val logisticInfo: String?,
    val dateTime: Date?,
    val state: OrderState,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photographer_id")
    val photographer: Photographer? = null
)