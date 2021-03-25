package com.salesianos.dam.trainerace.services

import com.salesianos.dam.trainerace.models.Comment
import com.salesianos.dam.trainerace.models.CreateCommentDTO
import com.salesianos.dam.trainerace.repository.CommentRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CommentService(private val commentRepository: CommentRepository) {

    fun create ( newComment : CreateCommentDTO) : Optional<Comment> {
        if ( commentRepository.findByTitulo(newComment.titulo).isPresent)
            return Optional.empty()
        return Optional.of(with ( newComment ) {
            commentRepository.save(Comment(titulo, cuerpo, "COMMENT"))
        })
    }

    fun findByTitulo(titulo : String) = commentRepository.findByTitulo(titulo)

    fun findAll () = commentRepository.findAll()

    fun save ( c : Comment) = commentRepository.save(c)

    fun findById(id : UUID) = commentRepository.findById(id)
}