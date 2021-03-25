package com.salesianos.dam.trainerace.controllers
import com.salesianos.dam.trainerace.models.CreateUserDTO
import com.salesianos.dam.trainerace.models.User
import com.salesianos.dam.trainerace.models.UserDTO
import com.salesianos.dam.trainerace.models.toUserDTO
import com.salesianos.dam.trainerace.repository.UserRepository
import com.salesianos.dam.trainerace.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/user")
class UserController(val userService: UserService) {

    @Autowired
    private lateinit var userRepository : UserRepository

    //EndPoint que permite listar todos los usuarios
    @GetMapping("/")
    fun getAll() : List<User> /*: ResponseEntity<List<Message>>*/ {
        var result = userRepository.findAll()
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
    //EndPoint que permite listar un usuario pasandole la id del mismo
    @GetMapping("/{id}")
    fun getById(@PathVariable id : UUID) =
            userRepository.findById(id)
                    .orElseThrow {
                        ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No hay ningún usuario con id: ${id}")
                    }

    //EndPoint que permite borrar un usuario pasandole su id
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id : UUID) : ResponseEntity<Any> {
        if (userRepository.existsById(id))
            userRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }
    //EndPoint que permite editar un usuario pasando su id
    @PutMapping("/{id}")
    fun edit(@PathVariable id: UUID,
             @RequestBody edited: User) : ResponseEntity<User> =
        userRepository.findById(id)
            .map { user ->
                user.nombre = edited.nombre
                user.apellidos = edited.apellidos
                user.email = edited.email
                user.fechaNacimiento = edited.fechaNacimiento
                user.sexo = edited.sexo
                ResponseEntity.ok(userRepository.save(user))
            }.orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No hay ningún mensaje con id: ${id}")
            }




}