package com.salesianos.dam.trainerace.models

import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
class Dieta (

    @get:NotBlank
    var nombre : String,
    var calorias : Double,
    @get:NotBlank
    var descripcion : String,
    var duracion : String,

    @ElementCollection(fetch = FetchType.EAGER)
    val roles: MutableSet<String> = HashSet(),

    @Id @GeneratedValue val id : UUID? = null

        ){

    constructor(nombre: String, calorias: Double, descripcion: String, duracion: String, role: String) : this(nombre, calorias, descripcion, duracion, mutableSetOf(role))
}