package com.marc.forment.photos.services

import com.marc.forment.photos.db.records.OrderRecord
import com.marc.forment.photos.db.repositories.OrderRepository
import com.marc.forment.photos.entities.Order
import org.springframework.stereotype.Service

@Service
class OrderService(private val orderRepository: OrderRepository) {
    fun findAllOrders(): List<Order> = orderRepository.selectOrders().map { Order(it.id, it.name) }
    fun saveOrder(order: Order) {
        orderRepository.save(OrderRecord(order.id, order.name))
    }
}