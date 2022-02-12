package com.marc.forment.photos.services

import com.marc.forment.photos.entities.NewOrder
import com.marc.forment.photos.entities.Order
import java.util.*

interface IOrderService {
    fun find(orderId: Long): Order
    fun findAllOrders(): List<Order>
    fun createOrder(newOrder: NewOrder)
    fun scheduleOrder(orderId: Long, dateTime: Date)
    fun assignOrder(orderId: Long, photographerId: Long)
    fun uploadOrder(orderId: Long)
    fun discardOrder(orderId: Long)
    fun verifyOrder(orderId: Long)
    fun cancelOrder(orderId: Long)
}