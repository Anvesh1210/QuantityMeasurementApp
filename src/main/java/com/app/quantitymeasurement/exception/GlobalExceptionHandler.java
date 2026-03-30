package com.app.quantitymeasurement.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	// Validation Errors
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex,
			WebRequest request) {

		List<String> messages = ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage)
				.collect(Collectors.toList());

		ErrorResponse response = ErrorResponse.builder().timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value()).error("Validation Error").errorCode("VALIDATION_ERROR")
				.message(String.join(", ", messages)).path(request.getDescription(false).replace("uri=", "")).build();

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	// Business Exception
	@ExceptionHandler(QuantityMeasurementException.class)
	public ResponseEntity<ErrorResponse> handleQuantityException(QuantityMeasurementException ex, WebRequest request) {

		ErrorResponse response = ErrorResponse.builder().timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value()).error("Quantity Measurement Error").errorCode(ex.getErrorCode())
				.message(ex.getMessage()).path(request.getDescription(false).replace("uri=", "")).build();

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	// Database Exception
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<ErrorResponse> handleDatabaseException(DatabaseException ex, WebRequest request) {

		ErrorResponse response = ErrorResponse.builder().timestamp(LocalDateTime.now())
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error("Database Error").errorCode(ex.getErrorCode())
				.message(ex.getMessage()).path(request.getDescription(false).replace("uri=", "")).build();

		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// Authentication Exception
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
		ErrorResponse response = ErrorResponse.builder().timestamp(LocalDateTime.now())
				.status(HttpStatus.UNAUTHORIZED.value()).error("Authentication Error").errorCode("AUTH_FAILED")
				.message("Invalid credentials").path(request.getDescription(false).replace("uri=", "")).build();

		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	// Generic Exception
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {

		ErrorResponse response = ErrorResponse.builder().timestamp(LocalDateTime.now())
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error("Internal Server Error")
				.errorCode("INTERNAL_ERROR").message(ex.getMessage())
				.path(request.getDescription(false).replace("uri=", "")).build();

		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
