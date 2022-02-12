package com.marc.forment.photos.controllers

import com.marc.forment.photos.controllers.contract.NewPhotographerRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

class PhotographerController() {
    @PostMapping("/photographers")
    fun createPhotographer(@RequestBody newPhotographerRequest: NewPhotographerRequest){

    }
}