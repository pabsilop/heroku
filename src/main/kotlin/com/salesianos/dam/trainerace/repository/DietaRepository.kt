package com.salesianos.dam.trainerace.repository

import com.salesianos.dam.trainerace.models.Dieta
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


interface DietaRepository : JpaRepository<Dieta, UUID> {

    fun findByNombre(nombre : String) : Optional<Dieta>
}