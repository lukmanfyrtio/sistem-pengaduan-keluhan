package com.swamedia.sistempengaduankeluhan.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseModel {

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@Column(name = "created_at", nullable = false, updatable = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:dd")
	@Schema(type = "string", pattern = "yyyy-MM-dd HH:mm:dd", example = "2023-01-01 23:59:59")
	private LocalDateTime createdAt;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@Column(name = "updated_at")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:dd")
	@Schema(type = "string", pattern = "yyyy-MM-dd HH:mm:dd", example = "2023-01-01 23:59:59")
	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

}
