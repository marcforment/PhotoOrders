package com.marc.forment.photos.services

import com.marc.forment.photos.db.repositories.OrderRepository
import com.marc.forment.photos.db.repositories.PhotographerRepository
import com.marc.forment.photos.entities.NewOrder
import com.marc.forment.photos.entities.Order
import com.marc.forment.photos.entities.OrderState
import com.marc.forment.photos.exceptions.InvalidState
import com.marc.forment.photos.exceptions.OrderNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class OrderService(
        private val orderRepository: OrderRepository,
        private val photographerService: IPhotographerService
) : IOrderService {
    override fun find(orderId: Long): Order {
        val result = orderRepository.findById(orderId)
        return if (result.isPresent) {
            result.get()
        }else {
            throw OrderNotFoundException(orderId)
        }
    }

    override fun findAllOrders(): List<Order> = orderRepository.findAll()
    override fun createOrder(newOrder: NewOrder) {
        saveOrder(newOrder.toOrder())
    }

    override fun scheduleOrder(orderId: Long, dateTime: Date) {
        val order = find(orderId)
        when(order.state) {
            OrderState.UNSCHEDULED -> saveOrder(order.copy(dateTime = dateTime, state = OrderState.PENDING))
            else -> throw InvalidState(order.state, OrderState.UNSCHEDULED)
        }

    }

    override fun assignOrder(orderId: Long, photographerId: Long) {
        val order = find(orderId)
        when(order.state) {
            OrderState.PENDING -> {
                val photographer = photographerService.find(photographerId)
                saveOrder(order.copy(photographer = photographer, state = OrderState.ASSIGNED))
            }
            else -> throw InvalidState(order.state, OrderState.PENDING)
        }
    }

    override fun uploadOrder(orderId: Long) {
        val order = find(orderId)
        when(order.state) {
            OrderState.ASSIGNED -> saveOrder(order.copy(state = OrderState.UPLOADED))
            else -> throw InvalidState(order.state, OrderState.ASSIGNED)
        }
    }

    override fun discardOrder(orderId: Long) {
        val order = find(orderId)
        when(order.state) {
            OrderState.UPLOADED -> saveOrder(order.copy(state = OrderState.ASSIGNED))
            else -> throw InvalidState(order.state, OrderState.UPLOADED)
        }
    }

    override fun verifyOrder(orderId: Long) {
        val order = find(orderId)
        when(order.state) {
            OrderState.UPLOADED -> saveOrder(order.copy(state = OrderState.COMPLETED))
            else -> throw InvalidState(order.state,OrderState.UPLOADED)
        }
    }

    override fun cancelOrder(orderId: Long) {
        val order = find(orderId)
        saveOrder(order.copy(state = OrderState.CANCELED))
    }
    private fun saveOrder(order: Order) {
        orderRepository.save(order)
    }

    private fun NewOrder.toOrder() : Order =
            Order(
                    name = this.name,
                    surname = this.surname,
                    email = this.email,
                    cellNumber = this.cellNumber,
                    photoType = this.photoType,
                    title = this.title,
                    logisticInfo = this.logisticInfo,
                    dateTime = this.dateTime,
                    state = when(this.dateTime){
                        null -> OrderState.UNSCHEDULED
                        else -> OrderState.PENDING
                    }
            )

}