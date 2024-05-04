package com.swamedia.sistempengaduankeluhan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swamedia.sistempengaduankeluhan.dto.Status;
import com.swamedia.sistempengaduankeluhan.model.Keluhan;
import com.swamedia.sistempengaduankeluhan.model.Trace;
import com.swamedia.sistempengaduankeluhan.repository.TraceRepository;

@Service
public class TraceService {

	@Autowired
	private TraceRepository traceRepository;

	public List<Trace> getTracesByComplaintNumber(String nomorKeluhan) {
		return traceRepository.findByNomorKeluhan(nomorKeluhan);
	}

	public void saveTraceWithKeluhan(Keluhan keluhan) {
		Status.fromDisplayName(keluhan.getStatus());
		Trace trace = new Trace();
		trace.setNomorKeluhan(keluhan.getNomor());
		trace.setStatus(keluhan.getStatus());
		traceRepository.save(trace);
	}

	@Transactional
	public void deleteByNomorKeluhan(String nomorKeluhan) {
		traceRepository.deleteByNomorKeluhan(nomorKeluhan);
	}

}