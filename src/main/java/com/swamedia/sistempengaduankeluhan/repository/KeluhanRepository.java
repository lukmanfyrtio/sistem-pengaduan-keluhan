package com.swamedia.sistempengaduankeluhan.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.swamedia.sistempengaduankeluhan.model.Keluhan;

@Repository
public interface KeluhanRepository extends JpaRepository<Keluhan, Long> {

	@Query(value = "SELECT MAX(u.id) FROM keluhan u", nativeQuery = true)
	Integer findMaxId();

	Page<Keluhan> findByStatus(String status, Pageable pageable);

	Page<Keluhan> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

	Page<Keluhan> findByStatusAndCreatedAtBetween(String status, LocalDateTime startDate, LocalDateTime endDate,
			Pageable pageable);

}
