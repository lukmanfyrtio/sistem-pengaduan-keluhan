package com.swamedia.sistempengaduankeluhan.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swamedia.sistempengaduankeluhan.dto.Bidang;
import com.swamedia.sistempengaduankeluhan.dto.Status;
import com.swamedia.sistempengaduankeluhan.model.Keluhan;
import com.swamedia.sistempengaduankeluhan.repository.KeluhanRepository;

@Service
public class KeluhanService {

	@Autowired
	private KeluhanRepository keluhanRepository;

	@Autowired
	private TraceService traceService;

	public Page<Keluhan> getAllKeluhan(Pageable pageable) {
		return keluhanRepository.findAll(pageable);
	}

	public Page<Keluhan> getKeluhanByCreatedAt(LocalDate createdAt, Pageable pageable) {
		return keluhanRepository.findByCreatedAtBetween(createdAt.atStartOfDay(), createdAt.atTime(23, 59, 59),
				pageable);
	}

	// Example of a method to filter Keluhan by status
	public Page<Keluhan> getKeluhanByStatus(String status, Pageable pageable) {
		return keluhanRepository.findByStatus(status, pageable);
	}

	public Page<Keluhan> getKeluhanByStatusAndCreatedAtRange(String status, LocalDate createdAt, Pageable pageable) {
		return keluhanRepository.findByStatusAndCreatedAtBetween(status, createdAt.atStartOfDay(),
				createdAt.atTime(23, 59, 59), pageable);
	}

	public Optional<Keluhan> getKeluhanById(Long id) {
		return keluhanRepository.findById(id);
	}

	public Keluhan saveKeluhan(Keluhan keluhan) {
		Bidang.fromDisplayName(keluhan.getBidang());
		Status.fromDisplayName(keluhan.getStatus());
		if (keluhan.getId() == null) {
			keluhan.setNomor(generateNomor(keluhan.getBidang()));
			traceService.saveTraceWithKeluhan(keluhan);
		}
		return keluhanRepository.save(keluhan);
	}

	@Transactional
	public void deleteKeluhan(Long id) {
		keluhanRepository.deleteById(id);
	}

	// Other business logic methods can be added here

	public int getLastIncrementFromDatabase() {
		Integer maxId = keluhanRepository.findMaxId();
		return maxId != null ? maxId : 0;
	}

	public String generateNomor(String bidang) {
		// Get current month and year
		LocalDate currentDate = LocalDate.now();
		int month = currentDate.getMonthValue();
		int year = currentDate.getYear();
		// Set nomor based on the format
		return generateIncrement() + "/" + Bidang.fromDisplayName(bidang).getKodeBidang() + "/" + month + "/" + year;
	}

	// Method to generate increment value - you need to implement this
	private int generateIncrement() {
		// Increment last increment value
		return getLastIncrementFromDatabase() + 1;
	}
}