package com.marc.forment.photos.services

import com.marc.forment.photos.entities.NewPhotographer
import com.marc.forment.photos.entities.Photographer

interface IPhotographerService {
    fun find(photographerId: Long): Photographer
    fun findAllPhotographers(): List<Photographer>
    fun createPhotographer(newPhotographer: NewPhotographer)
}