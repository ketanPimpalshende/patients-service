package com.root.patient.exception;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.stream.Collectors;

import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

	private static final String ERROR_CODE = "PS-{0}";

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionDetail> handleValidationException(final MethodArgumentNotValidException ex) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String methodName = request.getMethod();
		String path = request.getRequestURI();
//        log.error("correlationID :: " + correlationId + " tenantId :: " + tenantId + " " + methodName + " :: " + "Error occurred with message : " + ex.getMessage() + " and stack-trace : " + Arrays.toString(ex.getStackTrace()) +" Status code : " + HttpStatus.BAD_REQUEST.value(), ex);
		String errorDesc = ex.getBindingResult().getFieldErrors().stream()
				.map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
				.collect(Collectors.joining(", "));
		ExceptionDetail exceptionDetail = buildExceptionDetail(errorDesc,
				MessageFormat.format(ERROR_CODE, HttpStatus.BAD_REQUEST.value()), path);
		return new ResponseEntity<>(exceptionDetail, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(PlatformException.class)
	public ResponseEntity<ExceptionDetail> handlePlatformException(final PlatformException ex) {
	    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
	            .getRequest();
	    String methodName = request.getMethod();
	    String path = request.getRequestURI();

	    String errorDesc = ex.getMessage();
	    ExceptionDetail exceptionDetail = buildExceptionDetail(errorDesc,
	            MessageFormat.format(ERROR_CODE, ex.getCode()), path);
	    return new ResponseEntity<>(exceptionDetail, HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionDetail> handleGeneralException(final Exception ex) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String methodName = request.getMethod();
		String path = request.getRequestURI();
//	        log.error("correlationID :: " + correlationId + " tenantId :: " + tenantId + " " + methodName + " :: " + "Error occurred with message : " + ex.getMessage() + " and stack-trace : " + Arrays.toString(ex.getStackTrace()) + " Status code : " + HttpStatus.INTERNAL_SERVER_ERROR.value(), ex);
		String errorDesc = ex.getMessage();
		ExceptionDetail exceptionDetail = buildExceptionDetail(errorDesc,
				MessageFormat.format(ERROR_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value()), path);
		return new ResponseEntity<>(exceptionDetail, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ExceptionDetail buildExceptionDetail(String errorDesc, String errorCode, String path) {
		return new ExceptionDetail(errorDesc, errorCode, path, Instant.now().toString());
	}
}
