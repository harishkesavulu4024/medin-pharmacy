package com.medin.pharmacy.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import lombok.Data;

@Data
@MappedSuperclass
@SuppressWarnings("serial")
@Deprecated
public abstract class BaseEntityTest implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@CreatedBy
	@Column(name = "created_by")
	private String createdBy;

	@LastModifiedBy
	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "created_time")
	private Date createdTime;

	@Column(name = "modified_time")
	private Date modifiedTime;

	@PreUpdate
	public void updateAuditColumns() {
		modifiedBy = "SYSTEM";
		modifiedTime = new Date();
	}

	@PrePersist
	public void insertAuditColumns() {
		modifiedBy = "SYSTEM";
		modifiedTime = new Date();
		createdTime = modifiedTime;
		createdBy = modifiedBy;
	}

}