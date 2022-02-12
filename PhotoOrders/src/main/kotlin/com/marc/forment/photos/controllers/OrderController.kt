package com.marc.forment.photos.controllers

import com.marc.forment.photos.controllers.contract.AssignOrderRequest
import com.marc.forment.photos.controllers.contract.NewOrderRequest
import com.marc.forment.photos.controllers.contract.ScheduleOrderRequest
import com.marc.forment.photos.controllers.contract.UploadOrderRequest
import com.marc.forment.photos.entities.Order
import com.marc.forment.photos.exceptions.InvalidState
import com.marc.forment.photos.exceptions.NotInBusinessHours
import com.marc.forment.photos.exceptions.OrderNotFoundException
import com.marc.forment.photos.exceptions.PhotographerNotFoundException
import com.marc.forment.photos.services.IOrderService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class OrderController(private val orderService: IOrderService) {
    @GetMapping("/orders")
    fun getOrders(): List<Order> {
        return orderService.findAllOrders()
    }

    @GetMapping("/orders/{id}")
    fun getOrder(@PathVariable orderId: Long) : Order {
        return catchExceptions {
            orderService.find(orderId)
        }
    }

    @PostMapping("/orders")
    fun createOrder(@RequestBody newOrderRequest: NewOrderRequest) {
        catchExceptions {
            orderService.createOrder(newOrderRequest.toNewOrder())
        }
    }

    @PutMapping("/orders/{id}/schedule")
    fun scheduleOrder(@PathVariable orderId: Long, @RequestBody scheduleOrderRequest: ScheduleOrderRequest) {
        catchExceptions {
            orderService.scheduleOrder(orderId, scheduleOrderRequest.dateTime)
        }
    }

    @PutMapping("/orders/{id}/assign")
    fun assignOrder(@PathVariable orderId: Long, @RequestBody assignOrderRequest: AssignOrderRequest) {
        catchExceptions {
            orderService.assignOrder(orderId, assignOrderRequest.photographerId)
        }
    }

    @PutMapping("/orders/{id}/upload")
    fun uploadOrder(@PathVariable orderId: Long, @RequestBody uploadOrderRequest: UploadOrderRequest) {
        catchExceptions {
            orderService.uploadOrder(orderId)
        }
    }

    @PutMapping("/orders/{id}/discard")
    fun discardOrder(@PathVariable orderId: Long) {
        catchExceptions {
            orderService.discardOrder(orderId)
        }
    }

    @PutMapping("/orders/{id}/verify")
    fun verifyOrder(@PathVariable orderId: Long) {
        catchExceptions {
            orderService.verifyOrder(orderId)
        }
    }

    @PutMapping("/orders/{id}/cancel")
    fun cancelOrder(@PathVariable orderId: Long) {
        catchExceptions {
            orderService.cancelOrder(orderId)
        }
    }

    private fun <T> catchExceptions(block: () -> T) : T {
        try {
            return block()
        } catch (e: OrderNotFoundException){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        } catch (e: PhotographerNotFoundException){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        } catch (e: InvalidState) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        } catch (e: NotInBusinessHours) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }
}