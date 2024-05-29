package com.swamedia.sistempengaduankeluhan.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swamedia.sistempengaduankeluhan.dto.CustomResponse;
import com.swamedia.sistempengaduankeluhan.model.Kecamatan;
import com.swamedia.sistempengaduankeluhan.service.KecamatanService;

@RestController
@RequestMapping("/kecamatan")
public class KecamatanController {

	@Autowired
	private KecamatanService kecamatanService;

	@GetMapping
	public ResponseEntity<CustomResponse<List<Kecamatan>>> getAllKecamatan() {
		CustomResponse<List<Kecamatan>> response = new CustomResponse<>("Data retrieved successfully",
				kecamatanService.getAllKecamatan());
		return response.toResponseEntity(HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomResponse<Kecamatan>> getKecamatanById(@PathVariable Long id) {
		Optional<Kecamatan> kecamatan = kecamatanService.findById(id);
		if (kecamatan.isPresent()) {
			CustomResponse<Kecamatan> response = new CustomResponse<>("Success", kecamatan.get());
			return response.toResponseEntity(HttpStatus.OK);
		} else {
			CustomResponse<Kecamatan> response = new CustomResponse<>(String.format("Data with id : %s not found", id),
					null);
			return response.toResponseEntity(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/by-postal-code/{kodePos}")
	public ResponseEntity<CustomResponse<Kecamatan>> getKecamatanByKodePos(@PathVariable Long kodePos) {
		Optional<Kecamatan> kecamatan = kecamatanService.findByKodePos(kodePos);
		if (kecamatan.isPresent()) {
			CustomResponse<Kecamatan> response = new CustomResponse<>("Success", kecamatan.get());
			return response.toResponseEntity(HttpStatus.OK);
		} else {
			CustomResponse<Kecamatan> response = new CustomResponse<>(String.format("Data with kodePos : %s not found", kodePos),
					null);
			return response.toResponseEntity(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<CustomResponse<Kecamatan>> createKecamatan(@RequestBody Kecamatan kecamatan) {
		CustomResponse<Kecamatan> response = new CustomResponse<>("Data created successfully",
				kecamatanService.createKecamatan(kecamatan));
		return response.toResponseEntity(HttpStatus.CREATED);
	}
	
	@PostMapping("/bulk")
	public ResponseEntity<CustomResponse<List<Kecamatan>>> createKecamatan(@RequestBody List<Kecamatan> kecamatan) {
		CustomResponse<List<Kecamatan>> response = new CustomResponse<>("Data created successfully",
				kecamatanService.createKecamatanBulk(kecamatan));
		return response.toResponseEntity(HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CustomResponse<Kecamatan>> updateKecamatan(@PathVariable Long id,
			@RequestBody Kecamatan kecamatan) {
		Optional<Kecamatan> dataKecamatan = kecamatanService.findById(id);
		if (dataKecamatan.isEmpty()) {
			CustomResponse<Kecamatan> response = new CustomResponse<>(String.format("Data with id : %s not found", id),
					null);
			return response.toResponseEntity(HttpStatus.NOT_FOUND);
		}
		Kecamatan existingData = dataKecamatan.get();
		BeanUtils.copyProperties(kecamatan, existingData);
		Kecamatan uddateDataKecamatan = kecamatanService.updateKecamatan(id, existingData);
		CustomResponse<Kecamatan> response = new CustomResponse<>("Data updated successfully", uddateDataKecamatan);
		return response.toResponseEntity(HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CustomResponse<Void>> deleteKecamatan(@PathVariable Long id) {
		Optional<Kecamatan> dataKecamatan = kecamatanService.findById(id);
		if (dataKecamatan.isEmpty()) {
			CustomResponse<Void> response = new CustomResponse<>(String.format("Data with id : %s not found", id),
					null);
			return response.toResponseEntity(HttpStatus.NOT_FOUND);
		}
		kecamatanService.deleteKecamatan(id);
		CustomResponse<Void> response = new CustomResponse<>("Data deleted successfully", null);
		return response.toResponseEntity(HttpStatus.OK);
	}
}
