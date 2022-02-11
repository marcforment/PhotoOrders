package com.marc.forment.photos.db.repositories

import com.marc.forment.photos.db.records.OrderRecord
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface OrderRepository : CrudRepository<OrderRecord, String> {

    @Query("select id, name from orders")
    fun selectOrders(): List<OrderRecord>
}