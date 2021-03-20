package com.medin.pharmacy.enums;

public enum ProductUnit {

	PACKET("Pkt"), BOX("Box"), KG("Kg"), GRAMS("gms"), LITRE("Ltr"), ML("ml"), PIECES("Pcs");
	
	private String unitName;

	public static ProductUnit getValueOf(String unit) {
		for (ProductUnit unitObj : ProductUnit.values()) {
			if (unitObj.name().equalsIgnoreCase(unit)) {
				return unitObj;
			}
		}
		return null;
	}
	
	ProductUnit(String unitName){
		this.unitName=unitName;
	}

	@Override
	public String toString() {
		return this.name();
	}

}
