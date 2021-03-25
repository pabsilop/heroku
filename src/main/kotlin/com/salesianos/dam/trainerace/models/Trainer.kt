package com.salesianos.dam.trainerace.models

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank
import kotlin.collections.HashSet

@Entity
class Trainer(
    @get:NotBlank
    var nombre : String,
    var apellidos : String,
    var sexo : String,
    @get:NotBlank
    var especialidad : String,
    var edad : Int,
    var localidad: String,
    var precio: Double,
    var rating: String,
    var fechaNacimiento : String,

    @ElementCollection(fetch = FetchType.EAGER)
    val roles: MutableSet<String> = HashSet(),

    @Id @GeneratedValue val id : UUID? = null

) {

    constructor(nombre: String, apellidos: String, sexo: String, especialidad: String, edad: Int, localidad: String, precio: Double, rating : String, fechaNacimiento: String,  role : String) :
            this(nombre, apellidos, sexo, especialidad, edad, localidad, precio, rating, fechaNacimiento, mutableSetOf(role))




}