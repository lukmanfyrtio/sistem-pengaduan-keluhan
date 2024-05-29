package com.swamedia.sistempengaduankeluhan.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swamedia.sistempengaduankeluhan.model.Kecamatan;
import com.swamedia.sistempengaduankeluhan.repository.KecamatanRepository;

@Service
public class KecamatanService {

    @Autowired
    private KecamatanRepository kecamatanRepository;

    public List<Kecamatan> getAllKecamatan() {
        return kecamatanRepository.findAll();
    }

    public Kecamatan getKecamatanById(Long id) {
        return kecamatanRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Kecamatan not found"));
    }
    
	public Optional<Kecamatan> findById(Long id) {
		return kecamatanRepository.findById(id);
	}
	
	public Optional<Kecamatan> findByKodePos(Long kodePos) {
		return kecamatanRepository.findByKodePos(kodePos);
	}

    public Kecamatan createKecamatan(Kecamatan kecamatan) {
        return kecamatanRepository.save(kecamatan);
    }
    
    public List<Kecamatan> createKecamatanBulk(List<Kecamatan> kecamatan) {
        return kecamatanRepository.saveAll(kecamatan);
    }

    public Kecamatan updateKecamatan(Long id, Kecamatan kecamatan) {
        kecamatan.setId(id);
        return kecamatanRepository.save(kecamatan);
    }

    @Transactional
    public void deleteKecamatan(Long id) {
        kecamatanRepository.deleteById(id);
    }
}

