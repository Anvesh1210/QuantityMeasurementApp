package com.app.quantitymeasurement.exception;

import java.time.LocalDateTime;

public class ErrorResponse {

	private LocalDateTime timestamp;
	private int status;
	private String error;
	private String errorCode;
	private String message;
	private String path;

	public ErrorResponse() {
	}

	public ErrorResponse(LocalDateTime timestamp, int status, String error, String errorCode, String message,
			String path) {
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.errorCode = errorCode;
		this.message = message;
		this.path = path;
	}

	public static Builder builder() {
		return new Builder();
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public static final class Builder {
		private LocalDateTime timestamp;
		private int status;
		private String error;
		private String errorCode;
		private String message;
		private String path;

		private Builder() {
		}

		public Builder timestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public Builder status(int status) {
			this.status = status;
			return this;
		}

		public Builder error(String error) {
			this.error = error;
			return this;
		}

		public Builder errorCode(String errorCode) {
			this.errorCode = errorCode;
			return this;
		}

		public Builder message(String message) {
			this.message = message;
			return this;
		}

		public Builder path(String path) {
			this.path = path;
			return this;
		}

		public ErrorResponse build() {
			return new ErrorResponse(timestamp, status, error, errorCode, message, path);
		}
	}
}
