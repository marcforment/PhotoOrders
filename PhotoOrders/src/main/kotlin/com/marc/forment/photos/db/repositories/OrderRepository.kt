package com.marc.forment.photos.db.repositories

import com.marc.forment.photos.db.records.OrderRecord
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<OrderRecord, Int>