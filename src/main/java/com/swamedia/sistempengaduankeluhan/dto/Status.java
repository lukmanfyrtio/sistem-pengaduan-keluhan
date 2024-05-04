package com.swamedia.sistempengaduankeluhan.dto;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Status {
	OPEN("OPEN"), IN_PROGRESS("INPROGRESS"), REJECT("REJECT"), DONE("DONE");

	private final String displayName;

	Status(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public static Status fromDisplayName(String text) {
		for (Status status : Status.values()) {
			if (status.displayName.equalsIgnoreCase(text)) {
				return status;
			}
		}
		String availableValues = Arrays.stream(Status.values()).map(Status::getDisplayName)
				.collect(Collectors.joining(", "));
		throw new IllegalArgumentException("No constant with name '" + text
				+ "' found in status field. Available values are: " + availableValues);

	}
}
