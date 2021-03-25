package com.salesianos.dam.trainerace.repository

import com.salesianos.dam.trainerace.models.Trainer
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TrainerRepository : JpaRepository<Trainer, UUID> {

    fun findByNombre(nombre : String) : Optional<Trainer>
}