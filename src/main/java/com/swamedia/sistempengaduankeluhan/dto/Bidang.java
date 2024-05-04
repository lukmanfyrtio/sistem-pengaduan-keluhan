package com.swamedia.sistempengaduankeluhan.dto;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Bidang {
	PEMERINTAHAN_HUKUM("Pemerintahan dan Hukum", "PMH"),
	PERDAGANGAN_PERINDUSTRIAN("Perdagangan & Perindustrian", "PIN"), PEMBANGUNAN("Pembangunan", "PBG"),
	KESEJAHTERAAN_RAKYAT("Kesejahteraan Rakyat", "KSR");

	private final String displayName;
	private final String kodeBidang;

	Bidang(String displayName, String kodeBidang) {
		this.displayName = displayName;
		this.kodeBidang = kodeBidang;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getKodeBidang() {
		return kodeBidang;
	}

	public static Bidang fromDisplayName(String text) {
		for (Bidang bidang : Bidang.values()) {
			if (bidang.displayName.equalsIgnoreCase(text)) {
				return bidang;
			}
		}
		String availableValues = Arrays.stream(Bidang.values()).map(Bidang::getDisplayName)
				.collect(Collectors.joining(", "));
		throw new IllegalArgumentException("No constant with name '" + text
				+ "' found in bidang field. Available values are: " + availableValues);
	}

	public static Bidang fromKodeBidang(String text) {
		for (Bidang bidang : Bidang.values()) {
			if (bidang.kodeBidang.equalsIgnoreCase(text)) {
				return bidang;
			}
		}
		throw new IllegalArgumentException("No constant with kode bidang " + text + " found in Bidang enum");
	}
}
