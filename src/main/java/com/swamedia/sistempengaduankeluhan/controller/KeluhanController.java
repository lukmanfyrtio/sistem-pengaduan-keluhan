package com.swamedia.sistempengaduankeluhan.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swamedia.sistempengaduankeluhan.dto.CustomResponse;
import com.swamedia.sistempengaduankeluhan.dto.Status;
import com.swamedia.sistempengaduankeluhan.dto.UpdateKeluhanDTO;
import com.swamedia.sistempengaduankeluhan.model.Keluhan;
import com.swamedia.sistempengaduankeluhan.service.KeluhanService;
import com.swamedia.sistempengaduankeluhan.service.TraceService;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/keluhan")
public class KeluhanController {

	@Autowired
	private KeluhanService keluhanService;

	@Autowired
	private TraceService traceService;

	@GetMapping
	public ResponseEntity<CustomResponse<Page<Keluhan>>> getAllKeluhan(
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") @Parameter(example = "2023-01-01") LocalDate createdDate,
			@RequestParam(required = false) @Parameter(example = "OPEN") String status,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		if (createdDate != null && status != null) {
			Page<Keluhan> keluhans = keluhanService.getKeluhanByStatusAndCreatedAtRange(status, createdDate, pageable);
			CustomResponse<Page<Keluhan>> response = new CustomResponse<>("Success", keluhans);
			return response.toResponseEntity(HttpStatus.OK);
		} else if (createdDate != null) {
			Page<Keluhan> keluhans = keluhanService.getKeluhanByCreatedAt(createdDate, pageable);
			CustomResponse<Page<Keluhan>> response = new CustomResponse<>("Success", keluhans);
			return response.toResponseEntity(HttpStatus.OK);
		} else if (status != null) {
			try {
				Status.fromDisplayName(status);
				Page<Keluhan> keluhans = keluhanService.getKeluhanByStatus(status, pageable);
				CustomResponse<Page<Keluhan>> response = new CustomResponse<>("Success", keluhans);
				return response.toResponseEntity(HttpStatus.OK);
			} catch (IllegalArgumentException e) {
				CustomResponse<Page<Keluhan>> response = new CustomResponse<>(e.getMessage(), null);
				return response.toResponseEntity(HttpStatus.BAD_REQUEST);
			}
		}
		Page<Keluhan> keluhans = keluhanService.getAllKeluhan(pageable);
		CustomResponse<Page<Keluhan>> response = new CustomResponse<>("Success", keluhans);
		return response.toResponseEntity(HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomResponse<Keluhan>> getKeluhanById(@PathVariable Long id) {
		Optional<Keluhan> keluhan = keluhanService.getKeluhanById(id);
		if (keluhan.isPresent()) {
			CustomResponse<Keluhan> response = new CustomResponse<>("Success", keluhan.get());
			return response.toResponseEntity(HttpStatus.OK);
		} else {
			CustomResponse<Keluhan> response = new CustomResponse<>("Complaints not found", null);
			return response.toResponseEntity(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<CustomResponse<Keluhan>> createKeluhan(@RequestBody Keluhan keluhan) {
		try {
			keluhan.setStatus("OPEN");
			Keluhan createdKeluhan = keluhanService.saveKeluhan(keluhan);
			CustomResponse<Keluhan> response = new CustomResponse<>("Complaints created successfully", createdKeluhan);
			return response.toResponseEntity(HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			CustomResponse<Keluhan> response = new CustomResponse<>(e.getMessage(), null);
			return response.toResponseEntity(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			CustomResponse<Keluhan> response = new CustomResponse<>(e.getMessage(), null);
			return response.toResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<CustomResponse<Keluhan>> updateKeluhan(@PathVariable Long id,
			@RequestBody UpdateKeluhanDTO keluhanDetails) {
		try {
			Optional<Keluhan> existingKeluhanOptional = keluhanService.getKeluhanById(id);
			if (existingKeluhanOptional.isEmpty()) {
				CustomResponse<Keluhan> response = new CustomResponse<>("Complaints not found", null);
				return response.toResponseEntity(HttpStatus.NOT_FOUND);
			}
			Keluhan existingKeluhan = existingKeluhanOptional.get();
			existingKeluhan.setUraianKeluhan(keluhanDetails.getUraianKeluhan());

			if (!existingKeluhan.getStatus().equalsIgnoreCase(keluhanDetails.getStatus())) {
				existingKeluhan.setStatus(keluhanDetails.getStatus());
				traceService.saveTraceWithKeluhan(existingKeluhan);
			}

			Keluhan updatedKeluhan = keluhanService.saveKeluhan(existingKeluhan);
			CustomResponse<Keluhan> response = new CustomResponse<>("Complaints updated successfully", updatedKeluhan);
			return response.toResponseEntity(HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			CustomResponse<Keluhan> response = new CustomResponse<>(e.getMessage(), null);
			return response.toResponseEntity(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			CustomResponse<Keluhan> response = new CustomResponse<>(e.getMessage(), null);
			return response.toResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CustomResponse<Void>> deleteKeluhan(@PathVariable Long id) {
		Optional<Keluhan> keluhan = keluhanService.getKeluhanById(id);
		if (keluhan.isEmpty()) {
			CustomResponse<Void> response = new CustomResponse<>("Complaints not found", null);
			return response.toResponseEntity(HttpStatus.NOT_FOUND);
		}
		traceService.deleteByNomorKeluhan(keluhan.get().getNomor());
		keluhanService.deleteKeluhan(id);
		CustomResponse<Void> response = new CustomResponse<>("Complaints deleted successfully", null);
		return response.toResponseEntity(HttpStatus.OK);
	}

}
