package com.swamedia.sistempengaduankeluhan.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swamedia.sistempengaduankeluhan.dto.CustomResponse;
import com.swamedia.sistempengaduankeluhan.model.Penduduk;
import com.swamedia.sistempengaduankeluhan.repository.PendudukRepository;

@RestController
@RequestMapping("/dukcapil/data")
public class PendudukController {

	@Autowired
	private PendudukRepository pendudukRepository;

	@GetMapping
	public ResponseEntity<CustomResponse<List<Penduduk>>> getAllPenduduk() {
		CustomResponse<List<Penduduk>> response = new CustomResponse<>("Data retrieved successfully",
				pendudukRepository.findAll());
		return response.toResponseEntity(HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<CustomResponse<Penduduk>> addPenduduk(@RequestBody @Valid Penduduk penduduk) {
		try {
			validatePendudukAttributes(penduduk);
			Optional<Penduduk> existingDataPenduduk = pendudukRepository.findByNik(penduduk.getNik());
			if (existingDataPenduduk.isPresent()) {
				CustomResponse<Penduduk> response = new CustomResponse<>(
						String.format("Data with NIK : %s aready exist", penduduk.getNik()), null);
				return response.toResponseEntity(HttpStatus.CONFLICT);
			}
			CustomResponse<Penduduk> response = new CustomResponse<>("Data created successfully",
					pendudukRepository.save(penduduk));
			return response.toResponseEntity(HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			CustomResponse<Penduduk> response = new CustomResponse<>(e.getMessage(), null);
			return response.toResponseEntity(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new CustomResponse<>(e.getMessage(), null));
		}
	}

	@GetMapping("/{nik}")
	public ResponseEntity<CustomResponse<Penduduk>> getPendudukById(@PathVariable String nik) {
		Optional<Penduduk> penduduk = pendudukRepository.findByNik(nik);
		if (penduduk.isPresent()) {
			CustomResponse<Penduduk> response = new CustomResponse<>("Success", penduduk.get());
			return response.toResponseEntity(HttpStatus.OK);
		} else {
			CustomResponse<Penduduk> response = new CustomResponse<>(String.format("Data with NIK : %s not found", nik),
					null);
			return response.toResponseEntity(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{nik}")
	public ResponseEntity<CustomResponse<Penduduk>> updatePenduduk(@PathVariable String nik,
			@RequestBody Penduduk newPenduduk) {
		try {
			validatePendudukAttributes(newPenduduk);
			Optional<Penduduk> dataKependudukan = pendudukRepository.findByNik(nik);
			if (dataKependudukan.isEmpty()) {
				CustomResponse<Penduduk> response = new CustomResponse<>(
						String.format("Data with NIK : %s not found", nik), null);
				return response.toResponseEntity(HttpStatus.NOT_FOUND);
			}
			Penduduk existingData = dataKependudukan.get();
			newPenduduk.setId(existingData.getId());
			BeanUtils.copyProperties(newPenduduk, existingData);
			Penduduk uddateDataKependudukan = pendudukRepository.save(existingData);
			CustomResponse<Penduduk> response = new CustomResponse<>("Data updated successfully",
					uddateDataKependudukan);
			return response.toResponseEntity(HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			CustomResponse<Penduduk> response = new CustomResponse<>(e.getMessage(), null);
			return response.toResponseEntity(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new CustomResponse<>(e.getMessage(), null));
		}
	}

	@Transactional
	@DeleteMapping("/{nik}")
	public ResponseEntity<CustomResponse<Void>> deletePenduduk(@PathVariable String nik) {
		Optional<Penduduk> dataKependudukan = pendudukRepository.findByNik(nik);
		if (dataKependudukan.isEmpty()) {
			CustomResponse<Void> response = new CustomResponse<>(String.format("Data with NIK : %s not found", nik),
					null);
			return response.toResponseEntity(HttpStatus.NOT_FOUND);
		}
		pendudukRepository.deleteByNik(nik);
		CustomResponse<Void> response = new CustomResponse<>("Data deleted successfully", null);
		return response.toResponseEntity(HttpStatus.OK);
	}

	public static void validatePendudukAttributes(Penduduk penduduk) {
		if (penduduk == null) {
			throw new IllegalArgumentException("Penduduk entity cannot be null");
		}

		try {
			// Define the date format "yyyy-MM-dd"
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			// Parse the input string into a LocalDate object using the defined format
			LocalDate.parse(penduduk.getTanggalLahir(), formatter);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Tanggal lahir penduduk dalam format yyyy-MM-dd");
		}

		String jenisKelamin = penduduk.getJenisKelamin();
		String golonganDarah = penduduk.getGolonganDarah();
		String statusPerkawinan = penduduk.getStatus();

		if (jenisKelamin == null || jenisKelamin.isEmpty()
				|| (!jenisKelamin.equalsIgnoreCase("Laki-laki") && !jenisKelamin.equalsIgnoreCase("Perempuan"))) {
			throw new IllegalArgumentException("Invalid jenis kelamin. Must be Laki-laki or Perempuan");
		}

		if (golonganDarah == null || golonganDarah.isEmpty()) {
			throw new IllegalArgumentException("Golongan darah cannot be null or empty");
		}

		// Define array of valid golongan darah values
		String[] validGolonganDarah = { "A", "B", "AB", "O" };

		// Check if the provided golongan darah exists in the array
		boolean validGolonganDarahFlag = false;
		for (String validValue : validGolonganDarah) {
			if (validValue.equalsIgnoreCase(golonganDarah)) {
				validGolonganDarahFlag = true;
				break;
			}
		}

		if (!validGolonganDarahFlag) {
			throw new IllegalArgumentException("Invalid golongan darah. Must be A, B, AB, or O");
		}

		if (statusPerkawinan == null
				|| (!statusPerkawinan.equalsIgnoreCase("Belum Kawin") && !statusPerkawinan.equalsIgnoreCase("Kawin"))) {
			throw new IllegalArgumentException("Invalid status perkawinan. Must be Belum Kawin or Kawin");
		}
	}
}
