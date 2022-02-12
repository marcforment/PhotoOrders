package com.marc.forment.photos.db.records

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "orders")
data class OrderRecord (
        @Id @GeneratedValue( strategy = GenerationType.IDENTITY)
        val id: Long?,
        val name: String
)