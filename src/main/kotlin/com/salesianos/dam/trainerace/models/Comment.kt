package com.salesianos.dam.trainerace.models

import java.util.*
import javax.persistence.*
import kotlin.collections.HashSet

@Entity
class Comment (
    var titulo: String,
    var cuerpo: String,

    @ElementCollection(fetch = FetchType.EAGER)
    val roles: MutableSet<String> = HashSet(),

    @Id @GeneratedValue val id : UUID? = null
        ){
    constructor(titulo: String, cuerpo: String, role: String) : this(titulo, cuerpo, mutableSetOf(role))

}