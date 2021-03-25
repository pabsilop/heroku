package com.salesianos.dam.trainerace.exceptions

import org.apache.coyote.Response
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalRestControllerAdvice : ResponseEntityExceptionHandler() {

    override fun handleExceptionInternal(
        ex: Exception,
        body: Any?,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val apiError = ApiError(status, ex.message)
        return ResponseEntity.status(status).body(apiError)
    }

    @ExceptionHandler(value = [UserNotFoundException::class])
    fun handleNotFoundException( ex: UserNotFoundException ) =
        ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiError(HttpStatus.NOT_FOUND, ex.message))

    @ExceptionHandler(value = [UserAlreadyRegisteredException::class])
    fun handleExistsException( ex: UserAlreadyRegisteredException ) =
        ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ApiError(HttpStatus.CONFLICT, ex.message))

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> =
        ResponseEntity
            .status(status)
            .body(ApiError(
                status,
                "Ha ocurrido un error en la validación de los datos.",
                ex.fieldErrors.map {
                    ApiValidationSubError(it.objectName, it.field, it.rejectedValue, it.defaultMessage)
                }
            ))

}