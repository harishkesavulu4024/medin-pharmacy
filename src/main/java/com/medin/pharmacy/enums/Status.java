package com.medin.pharmacy.enums;

public enum Status {
	ACTIVE, INACTIVE;

	public static Status getValueOf(String status) {
		for (Status statusObj : Status.values()) {
			if (statusObj.name().equalsIgnoreCase(status)) {
				return statusObj;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return name();
	}

}
