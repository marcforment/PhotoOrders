package com.marc.forment.photos.db.records

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("ORDERS")
data class OrderRecord (
        @Id val id: String?,
        val name: String
)