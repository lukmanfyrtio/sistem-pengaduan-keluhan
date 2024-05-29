package com.swamedia.sistempengaduankeluhan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swamedia.sistempengaduankeluhan.model.Kecamatan;


public interface KecamatanRepository extends JpaRepository<Kecamatan, Long> {
    // You can add custom query methods if needed
	
	Optional<Kecamatan> findByKodePos(Long kodePos);
}