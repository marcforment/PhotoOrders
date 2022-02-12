package com.marc.forment.photos.entities

import javax.persistence.*

@Entity
@Table(name = "photographers")
data class Photographer(
        @Id @GeneratedValue( strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        val name: String
)
