package com.marc.forment.photos.controllers.contract

import com.marc.forment.photos.entities.NewPhotographer

data class NewPhotographerRequest(
        val name: String
) {
    fun toNewPhotographer(): NewPhotographer =
            NewPhotographer(this.name)
}
