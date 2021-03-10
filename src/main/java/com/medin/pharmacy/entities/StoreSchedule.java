package com.medin.pharmacy.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
//@Table(name = "store_schedule", indexes = { @Index(columnList = "store_id") }, uniqueConstraints = {
//		@UniqueConstraint(columnNames = { "store_id", "day_of_week" }) })
@Table(name = "store_schedule")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StoreSchedule extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "day_of_week", nullable = false)
	private String dayOfWeek;

	@Column(nullable = false)
	private String status;

	@Column(name = "open_time", nullable = false)
	private String openTime;

	@Column(name = "close_time", nullable = false)
	private String closeTime;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="store_id", nullable=false)
    private Store store;

}