package com.marc.forment.photos.controllers

import com.marc.forment.photos.entities.Order
import com.marc.forment.photos.services.OrderService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(private val orderService: OrderService) {
    @GetMapping("/orders")
    fun getOrders(): List<Order> {
        return orderService.findAllOrders()
    }

    @GetMapping("/orders/{id}")
    fun getOrder(@PathVariable id: Long) : Order {
        return Order("$id","Test")
    }

    @PostMapping("/orders")
    fun postOrder(@RequestBody order: Order) {
        orderService.saveOrder(order)
    }
}