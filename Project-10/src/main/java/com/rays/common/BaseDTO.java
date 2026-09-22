package com.rays.common;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * Base Data Transfer Object (DTO) class.
 * 
 * This class provides common fields for all entities such as:
 * - ID (Primary Key)
 * - Created By / Modified By
 * - Created DateTime / Modified DateTime
 * 
 * It is marked as @MappedSuperclass so that its properties
 * are inherited by all entity classes.
 * 
 * It also enforces implementation of methods for:
 * - Unique key handling
 * - Label representation
 * - Table name identification
 * 
 * Implements DropdownList to support dropdown rendering.
 * 
 * @author Abhishish Bhawsar
 */
@MappedSuperclass
public abstract class BaseDTO implements DropdownList {

	/**
	 * Primary key of the entity.
	 */
	@Id
	@GeneratedValue(generator = "ncsPk")
	@GenericGenerator(name = "ncsPk", strategy = "native")
	@Column(name = "id", unique = true, nullable = false)
	protected Long id;

	/**
	 * User who created the record.
	 */
	@Column(name = "created_by", length = 50)
	protected String createdBy = "root";

	/**
	 * User who last modified the record.
	 */
	@Column(name = "modified_by", length = 50)
	protected String modifiedBy = "root";

	/**
	 * Timestamp when the record was created.
	 */
	@Column(name = "created_datetime")
	protected Timestamp createdDatetime;

	/**
	 * Timestamp when the record was last modified.
	 */
	@Column(name = "modified_datetime")
	protected Timestamp modifiedDatetime;

	/**
	 * Returns unique key field name.
	 * 
	 * @return unique key
	 */
	public abstract String getUniqueKey();

	/**
	 * Returns value of the unique field.
	 * 
	 * @return unique value
	 */
	public abstract String getUniqueValue();

	/**
	 * Returns label used for display purposes.
	 * 
	 * @return label
	 */
	public abstract String getLabel();

	/**
	 * Returns table name associated with the DTO.
	 * 
	 * @return table name
	 */
	public abstract String getTableName();

	/**
	 * Gets ID.
	 * 
	 * @return ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets ID.
	 * 
	 * @param id primary key
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets createdBy.
	 * 
	 * @return createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets createdBy.
	 * 
	 * @param createdBy user name
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets modifiedBy.
	 * 
	 * @return modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * Sets modifiedBy.
	 * 
	 * @param modifiedBy user name
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * Gets createdDatetime.
	 * 
	 * @return timestamp
	 */
	public Timestamp getCreatedDatetime() {
		return createdDatetime;
	}

	/**
	 * Sets createdDatetime.
	 * 
	 * @param createdDatetime timestamp
	 */
	public void setCreatedDatetime(Timestamp createdDatetime) {
		this.createdDatetime = createdDatetime;
	}

	/**
	 * Gets modifiedDatetime.
	 * 
	 * @return timestamp
	 */
	public Timestamp getModifiedDatetime() {
		return modifiedDatetime;
	}

	/**
	 * Sets modifiedDatetime.
	 * 
	 * @param modifiedDatetime timestamp
	 */
	public void setModifiedDatetime(Timestamp modifiedDatetime) {
		this.modifiedDatetime = modifiedDatetime;
	}

	/**
	 * Returns key for dropdown list (used in UI).
	 * 
	 * @return string representation of ID
	 */
	public String getKey() {
		return String.valueOf(id);
	}
}