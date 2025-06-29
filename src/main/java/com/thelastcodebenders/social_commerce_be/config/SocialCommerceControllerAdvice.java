package com.thelastcodebenders.social_commerce_be.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thelastcodebenders.social_commerce_be.exceptions.EntityNotFoundException;
import com.thelastcodebenders.social_commerce_be.exceptions.UnauthorizedException;
import com.thelastcodebenders.social_commerce_be.models.dto.AppResponse;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class SocialCommerceControllerAdvice {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<AppResponse> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {

        log.error(ex.getMessage());
        AppResponse message = AppResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND).build();

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<AppResponse> handleUnauthorizedException(Exception ex, WebRequest request) {

        log.error(ex.getMessage());
        AppResponse message = AppResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.FORBIDDEN).build();

        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<AppResponse> handleExpiredJwtToken(Exception ex, WebRequest request) {

        log.error(ex.getMessage());
        AppResponse message = AppResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.UNAUTHORIZED).build();

        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<AppResponse> handleJsonProcessingException(JsonProcessingException ex, WebRequest request) {

        log.error(ex.getMessage());
        AppResponse message = AppResponse.builder()
                .message("Error parsing to Json/List")
                .status(HttpStatus.BAD_REQUEST).build();

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppResponse> handleGenericException(Exception ex, WebRequest request) {

        log.error(ex.getMessage());
        AppResponse message = AppResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
