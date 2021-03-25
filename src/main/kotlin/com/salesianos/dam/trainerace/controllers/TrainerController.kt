package com.salesianos.dam.trainerace.controllers

import com.salesianos.dam.trainerace.models.Dieta
import com.salesianos.dam.trainerace.models.Trainer
import com.salesianos.dam.trainerace.repository.TrainerRepository
import com.salesianos.dam.trainerace.services.TrainerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/trainer")
class TrainerController (val trainerService: TrainerService){

    @Autowired
    private lateinit var trainerRepository: TrainerRepository

    //EndPoint que permite crear un entrenador
    @PostMapping("/")
    fun create(@RequestBody trainer : Trainer) : ResponseEntity<Trainer> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(trainerRepository.save(trainer))

    //EndPoint que permite listar todos los entrenadores
    @GetMapping("/")
    fun getAll() : List<Trainer> /*: ResponseEntity<List<Message>>*/ {
        var result = trainerRepository.findAll()
        if (result.isNotEmpty()) {
            // devuelve el resultado con 200 OK
            //return ResponseEntity.ok(result)
            return result
        } else {
            // devuelvo un 404 y ¿mensaje de error?
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No hay mensajes"
            )
        }
    }

    //EndPoint que permite listar un entrenador pasandole la id del mismo
    @GetMapping("/{id}")
    fun getById(@PathVariable id : UUID) =
        trainerRepository.findById(id)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No hay ningún entrenador con id: ${id}")
            }
    //EndPoint que permite borrar un entrenador a través de su id
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id : UUID) : ResponseEntity<Any> {
        if (trainerRepository.existsById(id))
            trainerRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }
    //EndPoint que permite editar un entrenador
    @PutMapping("/{id}")
    fun edit(@PathVariable id: UUID,
             @RequestBody edited: Trainer
    ) : ResponseEntity<Trainer> =
        trainerRepository.findById(id)
            .map { trainer ->
                trainer.nombre = edited.nombre
                trainer.apellidos = edited.apellidos
                trainer.sexo = edited.sexo
                trainer.especialidad = edited.especialidad
                trainer.edad = edited.edad
                trainer.localidad = edited.localidad
                trainer.precio = edited.precio
                trainer.rating = edited.rating
                trainer.fechaNacimiento = edited.fechaNacimiento
                ResponseEntity.ok(trainerRepository.save(trainer))
            }.orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No hay ningun entrenador con id: ${id}")
            }
}