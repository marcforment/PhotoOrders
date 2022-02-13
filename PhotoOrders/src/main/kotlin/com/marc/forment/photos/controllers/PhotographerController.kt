package com.marc.forment.photos.controllers

import com.marc.forment.photos.controllers.contract.NewPhotographerRequest
import com.marc.forment.photos.entities.Photographer
import com.marc.forment.photos.exceptions.PhotographerNotFoundException
import com.marc.forment.photos.services.IPhotographerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class PhotographerController(private val photographerService: IPhotographerService) {

    @GetMapping("/photographers")
    fun getPhotographers(): List<Photographer> {
        return photographerService.findAllPhotographers()
    }

    @PostMapping("/photographers")
    fun createPhotographer(@RequestBody newPhotographerRequest: NewPhotographerRequest) {
        catchExceptions {
            photographerService.createPhotographer(newPhotographerRequest.toNewPhotographer())
        }
    }

    private fun <T> catchExceptions(block: () -> T): T {
        try {
            return block()
        } catch (e: PhotographerNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
}