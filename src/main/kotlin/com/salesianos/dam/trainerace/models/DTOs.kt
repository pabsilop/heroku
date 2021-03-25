package com.salesianos.dam.trainerace.models

import java.util.*

import com.salesianos.dam.trainerace.models.User
import java.time.LocalDate
import java.util.*
import javax.persistence.ElementCollection
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class UserDTO(
        var username: String,
        var password: String,
        var email: String,
        var sexo: String,
        var fechaNacimiento: String,
        var nombre: String,
        var apellidos: String,
        @Id @GeneratedValue
        val id: UUID? = null
)

fun User.toUserDTO() = UserDTO(username,password, email, sexo, fechaNacimiento, nombre, apellidos, id)

data class CreateUserDTO(
        @get:Size(min = 4, max = 18, message = "{usuario.username.blank}")
        var username: String,
        @get:NotBlank(message = "{usuario.password.blank}")
        @get:Size(min=4, max= 16, message = "{usuario.password.length}")
        var password: String,
        @get:Email(message = "{usuario.email.email}")
        var email : String,
        var sexo : String,
        var fechaNacimiento: String,
        var nombre: String,
        var apellidos: String,
)

data class TrainerDTO(

        var nombre : String,
        var apellidos : String,
        var sexo : String,
        var especialidad : String,
        var edad : Int,
        var localidad: String,
        var precio: Double,
        var rating: String,
        var fechaNacimiento : String,
        @Id @GeneratedValue
        val id: UUID? = null
)

fun Trainer.toTrainerDTO() = TrainerDTO(nombre,apellidos, sexo, especialidad, edad, localidad, precio, rating, fechaNacimiento, id)

data class CreateTrainerDTO(
        @get:Size(min = 4, max = 18, message = "{trainer.nombre.blank}")
        var nombre : String,
        var apellidos : String,
        var sexo : String,
        @get:Size(min=4, max= 50, message = "{trainer.especialidad.length}")
        var especialidad : String,
        var edad : Int,
        var localidad: String,
        var precio: Double,
        var rating: String,
        var fechaNacimiento : String,
)

data class DietaDTO(


        var nombre : String,
        var calorias : Double,
        var descripcion : String,
        var duracion : String,
        @Id @GeneratedValue
        val id : UUID? = null
)

fun Dieta.toDietaDTO() = DietaDTO(nombre, calorias, descripcion, duracion, id)

data class CreateDietaDTO(

        @get:Size(min = 4, max = 18, message = "{dieta.nombre.blank}")
        var nombre : String,
        var calorias : Double,
        var descripcion : String,
        var duracion : String,

)

data class CommentDTO(

        var titulo : String,
        var cuerpo : String,
        @Id @GeneratedValue
        val id : UUID? = null
)

fun Comment.toCommentDTO() = CommentDTO(titulo, cuerpo, id)

data class CreateCommentDTO(

        var titulo: String,
        var cuerpo: String
)