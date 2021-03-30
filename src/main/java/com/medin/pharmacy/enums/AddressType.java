package com.medin.pharmacy.enums;

public enum AddressType {
	
	HOME,
	BUSINESS,
	BILLING,
	SHIPPING,
	RECIPIENT,
	CONTRACT;
	
	public static AddressType getValueOf(String addressType) {
		for (AddressType addressTypeObj : AddressType.values()) {
			if (addressTypeObj.name().equalsIgnoreCase(addressType)) {
				return addressTypeObj;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return this.name();
	}

}
