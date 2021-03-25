package com.salesianos.dam.trainerace.exceptions

import java.lang.RuntimeException

open class UserNotFoundException(val msg : String) : RuntimeException(msg)
open class UserAlreadyRegisteredException(val msg : String) : RuntimeException(msg)

data class UserRegisteredException(
    val username : String
) : UserAlreadyRegisteredException("El nombre de usuario $username ya ha sido registrado anteriormente.")