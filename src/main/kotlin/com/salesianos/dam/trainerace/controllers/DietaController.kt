package com.salesianos.dam.trainerace.controllers

import com.salesianos.dam.trainerace.models.Dieta
import com.salesianos.dam.trainerace.models.Trainer
import com.salesianos.dam.trainerace.models.User
import com.salesianos.dam.trainerace.repository.DietaRepository
import com.salesianos.dam.trainerace.services.DietaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/dieta")
class DietaController(dietaService: DietaService) {

    @Autowired lateinit var dietaRepository: DietaRepository

    //EndPoint que permite mostrar todas las dietas
    @GetMapping("/")
    fun getAll() : List<Dieta> /*: ResponseEntity<List<Message>>*/ {
        var result = dietaRepository.findAll()
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
    //EndPoint que permite crear una nueva dieta
    @PostMapping("/")
    fun create(@RequestBody dieta : Dieta) : ResponseEntity<Dieta> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(dietaRepository.save(dieta))

    //EndPoint que permite mostrar una dieta a través de su id
    @GetMapping("/{id}")
    fun getById(@PathVariable id : UUID) =
        dietaRepository.findById(id)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No hay ninguna dieta con id: ${id}")
            }
    //EndPoint que permite borrar una dieta
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id : UUID) : ResponseEntity<Any> {
        if (dietaRepository.existsById(id))
            dietaRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    //EndPoint que permite editar una dieta
    @PutMapping("/{id}")
    fun edit(@PathVariable id: UUID,
             @RequestBody edited: Dieta) : ResponseEntity<Dieta> =
        dietaRepository.findById(id)
            .map { dieta ->
                dieta.nombre = edited.nombre
                dieta.calorias = edited.calorias
                dieta.descripcion = edited.descripcion
                dieta.duracion = edited.duracion
                ResponseEntity.ok(dietaRepository.save(dieta))
            }.orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No hay ninguna dieta con id: ${id}")
            }
}

