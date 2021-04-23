package com.medin.pharmacy.enums;

public enum OrderStatus {

	CART_DRAFT,
	CART_WAITING_PAYMENT,
	ORDER_CREATED,
	ORDER_DELIVERED,
	ORDER_DELIVERY_FOUND,
	ORDER_REJECTED,
	ORDER_SHIPPED, 
	ORDER_CANCELLED;

	public static OrderStatus getValueOf(String orderStatus) {
		for (OrderStatus orderStatusObj : OrderStatus.values()) {
			if (orderStatusObj.name().equalsIgnoreCase(orderStatus)) {
				return orderStatusObj;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return name();
	}

}
