package com.swamedia.sistempengaduankeluhan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swamedia.sistempengaduankeluhan.dto.CustomResponse;
import com.swamedia.sistempengaduankeluhan.model.Trace;
import com.swamedia.sistempengaduankeluhan.service.TraceService;

@RestController
@RequestMapping("/trace")
public class TraceController {

    @Autowired
    private TraceService traceService; // Assuming you have a TraceService

    @GetMapping("/by-complaint-number")
    public ResponseEntity<CustomResponse<List<Trace>>> getTracesByComplaintNumber(@RequestParam String nomorKeluhan) {
        List<Trace> traces = traceService.getTracesByComplaintNumber(nomorKeluhan);
        CustomResponse<List<Trace>> response = new CustomResponse<>("Success", traces);
		return response.toResponseEntity(HttpStatus.OK);
    }
}
