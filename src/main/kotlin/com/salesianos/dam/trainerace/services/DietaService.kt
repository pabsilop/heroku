package com.salesianos.dam.trainerace.services

import com.salesianos.dam.trainerace.models.CreateDietaDTO
import com.salesianos.dam.trainerace.models.Dieta
import com.salesianos.dam.trainerace.repository.DietaRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class DietaService(private val dietaRepository: DietaRepository) {


    fun create ( newDieta : CreateDietaDTO) : Optional<Dieta> {
        if ( dietaRepository.findByNombre(newDieta.nombre).isPresent)
            return Optional.empty()
        return Optional.of(with ( newDieta ) {
            dietaRepository.save(Dieta(nombre, calorias, descripcion, duracion, "DIETA"))
        })
    }

    fun findByName(nombre : String) = dietaRepository.findByNombre(nombre)

    fun findAll () = dietaRepository.findAll()

    fun save ( d : Dieta) = dietaRepository.save(d)

    fun findById(id : UUID) = dietaRepository.findById(id)
}