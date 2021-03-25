package com.salesianos.dam.trainerace.services

import com.salesianos.dam.trainerace.models.CreateTrainerDTO
import com.salesianos.dam.trainerace.models.Trainer
import com.salesianos.dam.trainerace.repository.TrainerRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class TrainerService(private val trainerRepository: TrainerRepository) {


    fun create ( newTrainer : CreateTrainerDTO) : Optional<Trainer> {
        if ( trainerRepository.findByNombre(newTrainer.nombre).isPresent)
            return Optional.empty()
        return Optional.of(with ( newTrainer ) {
            trainerRepository.save(Trainer(nombre, apellidos, sexo, especialidad, edad, localidad, precio, rating, fechaNacimiento, "TRAINER"))
        })
    }

    fun findByName(nombre : String) = trainerRepository.findByNombre(nombre)

    fun findAll () = trainerRepository.findAll()

    fun save ( t : Trainer ) = trainerRepository.save(t)

    fun findById(id : UUID) = trainerRepository.findById(id)
}