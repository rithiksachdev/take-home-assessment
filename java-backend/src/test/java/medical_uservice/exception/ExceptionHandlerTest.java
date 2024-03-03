package medical_uservice.exception;

import java.io.IOException;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.request.ServletWebRequest;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExceptionHandlerTest {

	@Test
	void whenHandleCustomException_thenResponseStatusIsBadRequest() {
		GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
		CustomException customException = new CustomException("Conflict occurred");

		ResponseEntity<ErrorResponse> response = exceptionHandler.handleCustomException(customException);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Conflict occurred", Objects.requireNonNull(response.getBody()).getMessage());
		assertEquals("Bad Request", response.getBody().getError());
	}

	@Test
	void whenHandlePatientNotFoundException_thenResponseStatusIsBadRequest() {
		GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
		PatientNotFoundException patientNotFoundException = new PatientNotFoundException("Patient not found");

		ResponseEntity<ErrorResponse> response = exceptionHandler.handlePatientNotFoundExceptionException(patientNotFoundException);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Patient not found", Objects.requireNonNull(response.getBody()).getMessage());
		assertEquals("Bad Request", response.getBody().getError());
	}

	@Test
	void whenHandleConstraintViolationException_thenResponseStatusIsBadRequest() throws IOException {
		// Arrange
		GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
		ConstraintViolationException constraintViolationException = new ConstraintViolationException("Validation failed", null);

		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		ServletWebRequest webRequest = new ServletWebRequest(request, response);

		// Act
		exceptionHandler.handleConstraintViolationException(constraintViolationException, webRequest);

		// Assert
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		assertEquals("Validation failed", response.getErrorMessage());
	}



}
