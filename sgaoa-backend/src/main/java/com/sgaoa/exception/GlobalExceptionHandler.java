package com.sgaoa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Erreur métier (email déjà utilisé, compte introuvable...)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .statut(HttpStatus.BAD_REQUEST.value())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    // Erreur de validation des champs (@NotBlank, @Email...)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(
            MethodArgumentNotValidException ex) {
        Map<String, String> erreurs = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String champ = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            erreurs.put(champ, message);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurs);
    }

    // Mauvais email ou mot de passe
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(
            BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.builder()
                        .statut(HttpStatus.UNAUTHORIZED.value())
                        .message("Email ou mot de passe incorrect.")
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    // Compte désactivé ou suspendu
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorResponse> handleDisabledException(
            DisabledException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.builder()
                        .statut(HttpStatus.FORBIDDEN.value())
                        .message("Votre compte est désactivé ou suspendu.")
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    // Erreur inattendue
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .statut(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Une erreur inattendue s'est produite.")
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}