package com.medin.pharmacy.enums;

public enum LedgerType {

	ISSUE,
	REDEEM,
	EXPIRY;

	public static LedgerType getValueOf(String ledgerType) {
		for (LedgerType ledgerTypeObj : LedgerType.values()) {
			if (ledgerTypeObj.name().equalsIgnoreCase(ledgerType)) {
				return ledgerTypeObj;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return name();
	}

}
