package com.marc.forment.photos

import com.marc.forment.photos.db.repositories.OrderRepository
import com.marc.forment.photos.db.repositories.PhotographerRepository
import com.marc.forment.photos.entities.Order
import com.marc.forment.photos.entities.OrderState
import com.marc.forment.photos.entities.PhotoType
import com.marc.forment.photos.entities.Photographer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@SpringBootTest
@AutoConfigureMockMvc
class PhotoOrderTests(@Autowired val mockMvc: MockMvc) {

    @Autowired
    private lateinit var orderRepository: OrderRepository

    @Autowired
    private lateinit var photographerRepository: PhotographerRepository

    @Test
    fun `Get All Photographers`() {
        val bob = stubPhotographer()
        photographerRepository.save(bob)

        mockMvc
                .perform(get("/photographers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("\$.[0].name").value(bob.name))
    }

    @Test
    fun `Get All Orders`() {
        val order = stubOrder(OrderState.UNSCHEDULED, dateTime = null)
        orderRepository.save(order)

        mockMvc
                .perform(get("/orders")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content()
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("\$.[0].name")
                .value(order.name))
    }

    @Test
    fun `Schedule Order`() {
        val order = stubOrder(OrderState.UNSCHEDULED, dateTime = null)
        orderRepository.save(order)

        mockMvc
                .perform(
                        put("/orders/1/schedule")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"dateTime\":\"2022-02-13T08:00:00\"}")
                )
                .andExpect(status().isOk)
        Assertions.assertEquals(OrderState.PENDING, orderRepository.findById(order.id!!).get().state)
    }

    @Test
    fun `Assign Order`() {
        val photographer = stubPhotographer()
        photographerRepository.save(photographer)
        val order = stubOrder(OrderState.PENDING)
        orderRepository.save(order)

        mockMvc
                .perform(
                        put("/orders/1/assign")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"photographerId\":\"${photographer.id}\"}")
                )
                .andExpect(status().isOk)
        Assertions.assertEquals(OrderState.ASSIGNED, orderRepository.findById(order.id!!).get().state)
    }

    @Test
    fun `Upload Order`() {
        val order = stubOrder(OrderState.ASSIGNED)
        orderRepository.save(order)

        mockMvc
                .perform(
                        put("/orders/1/upload")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"zip\":\"zipdata\"}")
                )
                .andExpect(status().isOk)
        Assertions.assertEquals(OrderState.UPLOADED, orderRepository.findById(order.id!!).get().state)
    }

    @Test
    fun `Discard Order`() {
        val order = stubOrder(OrderState.UPLOADED)
        orderRepository.save(order)

        mockMvc
                .perform(
                        put("/orders/1/discard")
                )
                .andExpect(status().isOk)
        Assertions.assertEquals(OrderState.ASSIGNED, orderRepository.findById(order.id!!).get().state)
    }

    @Test
    fun `Verify Order`() {
        val order = stubOrder(OrderState.UPLOADED)
        orderRepository.save(order)

        mockMvc
                .perform(
                        put("/orders/1/verify")
                )
                .andExpect(status().isOk)
        Assertions.assertEquals(OrderState.COMPLETED, orderRepository.findById(order.id!!).get().state)
    }

    @Test
    fun `Cancel Order`() {
        val order = stubOrder(OrderState.ASSIGNED)
        orderRepository.save(order)

        mockMvc
                .perform(
                        put("/orders/1/cancel")
                )
                .andExpect(status().isOk)
        Assertions.assertEquals(OrderState.CANCELED, orderRepository.findById(order.id!!).get().state)
    }

    private fun stubPhotographer() = Photographer(1,"Bob")
    private fun stubOrder(state: OrderState, id: Long = 1, dateTime: LocalDateTime? = LocalDateTime.now()): Order =
            Order(
                    id,
                    "Test",
                    "Order",
                    "testorder@gmail.com",
                    "+34555678654",
                    PhotoType.EVENTS,
                    "title",
                    "info",
                    dateTime, state = state)
    private fun localDateTimeFromString(dateTimeString: String) : LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return LocalDateTime.parse(dateTimeString, formatter)
    }
}
