package com.swamedia.sistempengaduankeluhan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Entity
public class Penduduk {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@Schema(description = "Identifikasi unik untuk penduduk", example = "1")
	private Long id;

	@NotBlank
	@Size(min = 16, max = 16)
	@Pattern(regexp = "\\d+", message = "Nomor Induk Kependudukan (NIK) harus berupa angka")
	@Schema(description = "Nomor Induk Kependudukan (NIK) penduduk", example = "3201010101000001")
	private String nik;

	@NotBlank
	@Size(min = 2, max = 255)
	@Schema(description = "Nama lengkap penduduk", example = "John Doe")
	private String nama;

	@NotBlank
	@Size(min = 2, max = 255)
	@Schema(description = "Tempat lahir penduduk", example = "Bandung")
	private String tempatLahir;

	@NotBlank
	@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Format tanggal lahir harus yyyy-MM-dd")
	@Schema(description = "Tanggal lahir penduduk dalam format yyyy-MM-dd", example = "1990-01-01")
	private String tanggalLahir;

	@NotBlank
	@Size(min = 1, max = 10)
	@Schema(description = "Jenis kelamin penduduk (Laki-laki/Perempuan)", example = "Laki-laki")
	private String jenisKelamin;

	@NotBlank
	@Size(min = 2, max = 50)
	@Schema(description = "Agama penduduk", example = "Islam")
	private String agama;

	@NotBlank
	@Size(min = 2, max = 50)
	@Schema(description = "Status perkawinan penduduk", example = "Kawin")
	private String status;

	@NotBlank
	@Size(min = 1, max = 3)
	@Schema(description = "Golongan darah penduduk", example = "O")
	private String golonganDarah;

	@NotBlank
	@Size(min = 2, max = 255)
	@Schema(description = "Alamat lengkap penduduk", example = "Jl. Jend. Sudirman No. 1, Jakarta")
	private String alamat;

	@NotBlank
	@Size(min = 2, max = 255)
	@Schema(description = "Pekerjaan penduduk", example = "Insinyur Perangkat Lunak")
	private String pekerjaan;
	
	@NotBlank
	@Column(name = "email")
	@Schema(example = "john_doe@yopmail.com")
	private String email;

	@NotBlank
	@Column(name = "no_wa")
	@Schema(example = "089778662551")
	private String noWa;
}
