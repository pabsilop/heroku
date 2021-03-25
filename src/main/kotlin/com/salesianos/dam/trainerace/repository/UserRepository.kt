package com.salesianos.dam.trainerace.repository
import com.salesianos.dam.trainerace.models.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {

    fun findByUsername(username : String) : Optional<User>


}