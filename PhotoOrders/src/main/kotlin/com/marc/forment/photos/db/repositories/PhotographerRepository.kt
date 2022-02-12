package com.marc.forment.photos.db.repositories

import com.marc.forment.photos.entities.Photographer
import org.springframework.data.jpa.repository.JpaRepository

interface PhotographerRepository : JpaRepository<Photographer, Long>