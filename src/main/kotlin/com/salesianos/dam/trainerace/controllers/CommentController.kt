package com.salesianos.dam.trainerace.controllers

import com.salesianos.dam.trainerace.models.Comment
import com.salesianos.dam.trainerace.models.Dieta
import com.salesianos.dam.trainerace.repository.CommentRepository
import com.salesianos.dam.trainerace.repository.DietaRepository
import com.salesianos.dam.trainerace.services.CommentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/comment")
class CommentController(commmentService: CommentService) {

    @Autowired
    lateinit var commentRepository: CommentRepository

    //EndPoint que permite mostrar todos los usuarios
    @GetMapping("/")
    fun getAll() : List<Comment> /*: ResponseEntity<List<Message>>*/ {
        var result = commentRepository.findAll()
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
    //EndPoint que permite crear un nuevo comentario
    @PostMapping("/")
    fun create(@RequestBody comment : Comment) : ResponseEntity<Comment> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentRepository.save(comment))

    //EndPoint que permite mostrar un comentario a través de su id
    @GetMapping("/{id}")
    fun getById(@PathVariable id : UUID) =
        commentRepository.findById(id)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No hay ningun comentario con id: ${id}")
            }
    //EndPoint que permite borrar un comentario
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id : UUID) : ResponseEntity<Any> {
        if (commentRepository.existsById(id))
            commentRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    //EndPoint que permite editar un comentario
    @PutMapping("/{id}")
    fun edit(@PathVariable id: UUID,
             @RequestBody edited: Comment
    ) : ResponseEntity<Comment> =
        commentRepository.findById(id)
            .map { comment ->
                comment.titulo = edited.titulo
                comment.cuerpo = edited.cuerpo
                ResponseEntity.ok(commentRepository.save(comment))
            }.orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No hay ningun comentario con id: ${id}")
            }
}