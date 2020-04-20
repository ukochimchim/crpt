package com.example.dto;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.util.CollectionUtils;

public class Header {

	private ResponseCode responseCode;

	private String message;

	private Header() {
		super();
	}

	private Header(ResponseCode code, String message) {
		this();
		this.responseCode = code;
		this.message = message;
	}

	public ResponseCode getResponseCode() {
		return responseCode;
	}

	public String getMessage() {
		return message;
	}

	public static class Builder {

		private ResponseCode code;

		private String message;

		private Set<ConstraintViolation<Document>> errors;

		public Builder setResponseCode(ResponseCode code) {
			this.code = code;
			return this;
		}

		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		public Builder setValidationErrors(Set<ConstraintViolation<Document>> errors) {
			this.errors = errors;
			return this;
		}

		public Header build() {
			if (this.code == null) {
				this.code = ResponseCode.OK;
			}
			if (!CollectionUtils.isEmpty(errors)) {
				this.code = ResponseCode.VALIDATION_ERROR;
				this.message = errors.stream().collect(StringBuilder::new, (buffer, error) -> {
					buffer.append("\r\n").append(error.getPropertyPath()).append("=").append(error.getMessage());
				}, StringBuilder::append).toString();
			}
			return new Header(this.code, this.message);

		}

	}

}
