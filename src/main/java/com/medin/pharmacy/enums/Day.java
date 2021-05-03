package com.medin.pharmacy.enums;


public enum Day {
	MONDAY,
	TUESDAY,
	WEDNESDAY,
	THURSDAY,
	FRIDAY,
	SATURDAY,
	SUNDAY;
	private Day() {
	}

	public String getday() {
		return name();
	}

	public static Day getDay(int dayIndex) {
		for (Day day : Day.values()) {
			if (day.ordinal() == dayIndex) {
				return day;
			}
		}
		return null;
	}

	public static Day getValueOf(String dayValue) {
		for (Day day : Day.values()) {
			if (day.name().equalsIgnoreCase(dayValue)) {
				return day;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return this.name();
	}
}
