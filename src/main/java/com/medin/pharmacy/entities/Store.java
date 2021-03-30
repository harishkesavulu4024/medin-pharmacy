package com.medin.pharmacy.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.medin.pharmacy.enums.Status;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "store")
@EntityListeners(AuditingEntityListener.class)
public class Store extends BaseEntity<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "image_url", nullable = false)
	private String imageUrl;

	@Column(name = "store_code", nullable = false, unique = true)
	private String storeCode;

	@Column(nullable = false, unique = true)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, columnDefinition = "varchar(40) default 'ACTIVE'")
	private Status status;

	@Column(name = "store_type")
	private String storeType;

	@Column(name = "short_name")
	private String shortName;

	private String latitude;

	private String longitude;

	private String rating;

	private String description;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="address_id",nullable = true)
	private Address address;

	@Setter(AccessLevel.NONE)
	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StoreSchedule> storcheules;

	public void setStorcheules(List<StoreSchedule> storcheules) {
		this.storcheules = storcheules;
		if (this.storcheules != null) {
			for (StoreSchedule storSchedule : this.storcheules) {
				storSchedule.setStore(this);
			}
		}
	}

}
