package com.salesianos.dam.trainerace.models

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank
import kotlin.collections.HashSet

@Entity
class User(
        @Column(nullable = false, unique = true)
        @get:NotBlank
        private var username: String,
        @get:NotBlank
        private var password: String,
        var email : String,
        var sexo : String,
        var fechaNacimiento : String,
        var nombre : String,
        var apellidos : String,

        @ElementCollection(fetch = FetchType.EAGER)
        val roles: MutableSet<String> = HashSet(),

        private val nonExpired: Boolean = true,

        private val nonLocked: Boolean = true,

        private val enabled: Boolean = true,

        private val credentialsNonExpired : Boolean = true,

        @Id @GeneratedValue val id : UUID? = null

) : UserDetails {

    constructor(username: String, password: String, email: String, sexo: String, fechaNacimiento: String, nombre: String, apellidos: String, role : String) :
            this(username, password, email, sexo, fechaNacimiento, nombre, apellidos, mutableSetOf(role), true, true, true, true)


    override fun isEnabled() = enabled
    override fun getUsername() = username
    override fun isCredentialsNonExpired() = credentialsNonExpired
    override fun getPassword() = password
    override fun isAccountNonExpired() = nonExpired
    override fun isAccountNonLocked() = nonLocked


    /**
     * En JPA, dos entidades deberían considerarse iguales
     * sí tienen en mismo ID
     */

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other === null || other !is User)
            return false
        if (this::class != other::class)
            return false
        return id == other.id
    }

    override fun hashCode(): Int {
        if (id == null)
            return super.hashCode()
        return id.hashCode()
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
            roles.map { SimpleGrantedAuthority("ROLE_$it") }.toMutableList()


}