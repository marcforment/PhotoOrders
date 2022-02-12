package com.marc.forment.photos.services

import com.marc.forment.photos.db.repositories.PhotographerRepository
import com.marc.forment.photos.entities.NewPhotographer
import com.marc.forment.photos.entities.Photographer
import com.marc.forment.photos.exceptions.PhotographerNotFoundException
import org.springframework.stereotype.Service

@Service
class PhotographerService(private val photographerRepository: PhotographerRepository) : IPhotographerService {
    override fun find(photographerId: Long): Photographer {
        val result = photographerRepository.findById(photographerId)
        return if (result.isPresent) {
            result.get()
        }else {
            throw PhotographerNotFoundException(photographerId)
        }
    }

    override fun createPhotographer(newPhotographer: NewPhotographer) {
        photographerRepository.save(newPhotographer.toPhotographer())
    }

    private fun NewPhotographer.toPhotographer() : Photographer = Photographer(name = this.name)
}