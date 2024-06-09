package com.root.patient.exception;

import lombok.Getter;

@Getter
public class PlatformException extends RuntimeException {
	
	private String code;
	private String message;

	public PlatformException(String code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public PlatformException(String code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}
	
	public PlatformException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
		this.message = message;
	}
	
}
