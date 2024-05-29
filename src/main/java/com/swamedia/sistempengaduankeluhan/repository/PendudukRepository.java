package com.swamedia.sistempengaduankeluhan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.swamedia.sistempengaduankeluhan.model.Penduduk;

public interface PendudukRepository extends JpaRepository<Penduduk, Long> {
	Optional<Penduduk> findByNik(String nik);
	
	@Modifying
	void deleteByNik(String nik);
}
