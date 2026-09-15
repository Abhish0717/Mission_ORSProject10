package com.rays.common;

import org.springframework.dao.DataAccessResourceFailureException;
import org.hibernate.exception.JDBCConnectionException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global Exception Handler for the application.
 * This class handles database-related and runtime exceptions
 * and returns standardized API responses.
 * 
 * It uses @RestControllerAdvice to intercept exceptions
 * thrown by REST controllers across the application.
 * 
 * @author Abhishish Bhawsar
 */
@RestControllerAdvice
public class ApplicationExceptionHandlerCtl {

	/**
	 * Handles all database-related exceptions such as:
	 * - CannotCreateTransactionException
	 * - DataAccessResourceFailureException
	 * - JDBCConnectionException
	 * 
	 * Returns HTTP 503 (Service Unavailable) with a user-friendly message.
	 * 
	 * @param e Exception object
	 * @return ResponseEntity containing ORSResponse with error details
	 */
	@ExceptionHandler({ CannotCreateTransactionException.class, DataAccessResourceFailureException.class,
			JDBCConnectionException.class })
	public ResponseEntity<ORSResponse> handleDatabaseException(Exception e) {

		ORSResponse res = new ORSResponse(false);
		res.addMessage("Database service is currently unavailable. Please try again later.");

		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE) // 503
				.body(res);
	}

	/**
	 * Handles all uncaught runtime exceptions.
	 * 
	 * Returns HTTP 500 (Internal Server Error) with the exception message.
	 * 
	 * @param e RuntimeException object
	 * @return ResponseEntity containing ORSResponse with error details
	 */
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ORSResponse> handleRuntimeException(RuntimeException e) {

		ORSResponse res = new ORSResponse(false);
		e.printStackTrace();
		res.addMessage(e.getMessage());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // 500
				.body(res);
	}
}