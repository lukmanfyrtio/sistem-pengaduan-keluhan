package com.swamedia.sistempengaduankeluhan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.swamedia.sistempengaduankeluhan.model.Trace;

@Repository
public interface TraceRepository extends JpaRepository<Trace, Long> {
	List<Trace> findByNomorKeluhan(String nomorKeluhan);
	@Modifying
	void deleteByNomorKeluhan(String nomorKeluhan);
}