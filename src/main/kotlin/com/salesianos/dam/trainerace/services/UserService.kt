package com.salesianos.dam.trainerace.services


import com.salesianos.dam.trainerace.models.CreateTrainerDTO
import com.salesianos.dam.trainerace.models.CreateUserDTO
import com.salesianos.dam.trainerace.models.User
import com.salesianos.dam.trainerace.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class UserService(private val userRepository: UserRepository, private val encoder: PasswordEncoder) {


    fun create ( newUser : CreateUserDTO ) : Optional<User> {
        if ( userRepository.findByUsername(newUser.username).isPresent)
            return Optional.empty()
        return Optional.of(with ( newUser ) {
            userRepository.save(User(username, encoder.encode(password), email,sexo, fechaNacimiento, nombre, apellidos,"USER"))
        })
    }
    /*
    fun createTrainer ( newUser : CreateUserDTO ) : Optional<User> {
        if ( userRepository.findByUsername(newUser.username).isPresent)
            return Optional.empty()
        return Optional.of(with ( newUser ) {
            userRepository.save(User(username, encoder.encode(password), email,sexo, fechaNacimiento, nombre, apellidos,"TRAINER"))
        })
    }*/

    fun findByUsername(username : String) = userRepository.findByUsername(username)

    fun findAll () = userRepository.findAll()

    fun save ( u : User ) = userRepository.save(u)

    fun findById(id : UUID) = userRepository.findById(id)
}