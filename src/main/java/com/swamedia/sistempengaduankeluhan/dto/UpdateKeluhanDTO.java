package com.swamedia.sistempengaduankeluhan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateKeluhanDTO {
	@Schema(example = "Air kotor")
	private String uraianKeluhan;

	@Schema(example = "OPEN")
	private String status;
}
