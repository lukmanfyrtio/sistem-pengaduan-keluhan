package com.swamedia.sistempengaduankeluhan.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;

public class CustomResponse<T> {

	@Schema(type = "string", pattern = "yyyy-MM-dd HH:mm:dd", example = "2023-01-01 23:59:59")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:dd")
	private LocalDateTime timestamp;
	@Schema(example = "Success")
	private String message;
	@JsonInclude(Include.NON_NULL)
	private T data;

	public CustomResponse(String message, T data) {
		this.timestamp = LocalDateTime.now();
		this.message = message;
		this.data = data;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public ResponseEntity<CustomResponse<T>> toResponseEntity(HttpStatus status) {
		return new ResponseEntity<>(this, status);
	}
}
