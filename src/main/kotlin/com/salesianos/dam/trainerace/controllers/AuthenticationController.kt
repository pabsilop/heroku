 package com.salesianos.dam.trainerace.controllers


import com.salesianos.dam.trainerace.exceptions.UserRegisteredException
import com.salesianos.dam.trainerace.models.CreateUserDTO
import com.salesianos.dam.trainerace.models.User
import com.salesianos.dam.trainerace.models.UserDTO
import com.salesianos.dam.trainerace.models.toUserDTO
import com.salesianos.dam.trainerace.security.jwt.BearerTokenExtractor
import com.salesianos.dam.trainerace.security.jwt.JwtTokenProvider
import com.salesianos.dam.trainerace.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import javax.validation.constraints.NotBlank

 @RestController
@RequestMapping("/auth")
class AuthenticationController(
         private val authenticationManager: AuthenticationManager,
         private val jwtTokenProvider: JwtTokenProvider,
         private val bearerTokenExtractor: BearerTokenExtractor,
         private val userService: UserService
) {

    @PostMapping("/register")
    fun nuevoUsuario(@Valid @RequestBody newUser: CreateUserDTO) : ResponseEntity<UserDTO> =
        userService.create(newUser).map {
            println(it.toUserDTO())
            ResponseEntity.status(HttpStatus.CREATED)
                .body(it.toUserDTO())
        }.orElseThrow {
            UserRegisteredException(newUser.username)
        }
    /* @PostMapping("/register/trainer")
     fun nuevoEntrenador(@Valid @RequestBody newUser: CreateUserDTO) : ResponseEntity<UserDTO> =
         userService.create(newUser).map {
             println(it.toUserDTO())
             ResponseEntity.status(HttpStatus.CREATED)
                 .body(it.toUserDTO())
         }.orElseThrow {
             UserRegisteredException(newUser.username)
         }*/
    @PostMapping("/login")
    fun login ( @Valid @RequestBody loginRequest : LoginRequest ) : ResponseEntity<JwtUserResponse> {
        println(loginRequest.toString())
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequest.username, loginRequest.password
            )
        )

        SecurityContextHolder.getContext().authentication = authentication

        val user = authentication.principal as User
        val jwtToken = jwtTokenProvider.generateToken(user)
        val jwtRefreshToken = jwtTokenProvider.generateRefreshToken(user)

        return ResponseEntity.status(HttpStatus.CREATED).body(JwtUserResponse(jwtToken, jwtRefreshToken, user.toUserDTO()))
    }

    @PostMapping("/token")
    fun refreshToken(request : HttpServletRequest) : ResponseEntity<JwtUserResponse> {

        val refreshToken = bearerTokenExtractor.getJwtFromRequest(request).orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al procesar el token de refresco")
        }

        try {
            if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
                val userId = jwtTokenProvider.getUserIdFromJWT(refreshToken)
                val user: User = userService.findById(userId).orElseThrow {
                    UsernameNotFoundException("No se ha podido encontrar el usuario a partir de su ID")
                }
                val jwtToken = jwtTokenProvider.generateToken(user)
                val jwtRefreshToken = jwtTokenProvider.generateRefreshToken(user)

                return ResponseEntity.status(HttpStatus.CREATED).body(JwtUserResponse(jwtToken, jwtRefreshToken, user.toUserDTO()))
            }
        } catch (ex : Exception) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Error en la validación del token")
        }

        return ResponseEntity.badRequest().build()

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/me")
    fun me(@AuthenticationPrincipal user : User) = user.toUserDTO()

}



data class LoginRequest(
    @NotBlank val username : String,
    @NotBlank val password: String
)

data class JwtUserResponse(
    val token: String,
    val refreshToken: String,
    val user : UserDTO
)