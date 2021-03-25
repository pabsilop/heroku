package com.salesianos.dam.trainerace.repository

import com.salesianos.dam.trainerace.models.Comment
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CommentRepository : JpaRepository<Comment, UUID> {

    fun findByTitulo(titulo : String) : Optional<Comment>
}
