package com.marc.forment.photos.db.repositories

import com.marc.forment.photos.entities.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long>