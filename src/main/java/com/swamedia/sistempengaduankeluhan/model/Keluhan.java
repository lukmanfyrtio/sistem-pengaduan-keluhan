package com.swamedia.sistempengaduankeluhan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "keluhan")
public class Keluhan extends BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@Schema(example = "1")
	private Long id;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@Column(name = "nomor")
	@Schema(example = "1/PBG/5/2024")
	private String nomor;

	@Column(name = "bidang")
	@Schema(example = "Pembangunan")
	private String bidang;

	@Column(name = "alamat_keluhan")
	@Schema(example = "Jl. Merdeka No 10")
	private String alamatKeluhan;

	@Column(name = "nama_kecamatan")
	@Schema(example = "Kiaracondong")
	private String namaKecamatan;

	@Column(name = "nama_pelapor")
	@Schema(example = "John Doe")
	private String namaPelapor;

	@Column(name = "email_pelapor")
	@Schema(example = "john_doe@yopmail.com")
	private String emailPelapor;

	@Column(name = "wa_pelapor")
	@Schema(example = "089778662551")
	private String waPelapor;

	@Column(name = "uraian_keluhan")
	@Schema(example = "Air kotor")
	private String uraianKeluhan;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@Schema(example = "OPEN")
	@Column(name = "status")
	private String status;

}
