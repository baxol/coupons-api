package com.jb.coupons.common

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@RestControllerAdvice("com.jb.coupons")
class ApiGlobalExceptionHandler {

    fun logger(): Logger = LoggerFactory.getLogger(ApiGlobalExceptionHandler::class.java)

    @ExceptionHandler(ApiException::class)
    fun handleApiException(ex: ApiException): ResponseEntity<ApiError> =
        ResponseEntity
            .status(ex.httpStatus)
            .body(
                ApiError(
                    ex.httpStatus,
                    ex.errorType,
                    ex.errorMessage,
                    ex.detail
                )
            )

    @ExceptionHandler(
        HttpMessageNotReadableException::class,
        MethodArgumentTypeMismatchException::class,
        MissingServletRequestParameterException::class,
        MethodArgumentNotValidException::class
    )
    fun handleBadRequest(ex: Exception): ResponseEntity<ApiError> =
        ResponseEntity
            .badRequest()
            .body(
                ApiError(
                    HttpStatus.BAD_REQUEST,
                    ErrorType.BAD_REQUEST,
                    null,
                    ex.message,
                )
            )

    @ExceptionHandler(Exception::class)
    fun handleUnexpected(ex: Exception): ResponseEntity<ApiError> {
        logger().error("Unhandled exception", ex)

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorType.UNKNOWN,
                    ErrorMessage.INTERNAL_SERVER_ERROR
                )
            )
    }


}