package com.rays.dto;

import java.io.IOException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.springframework.web.multipart.MultipartFile;

import com.rays.common.BaseDTO;

/**
 * DTO class for Attachment entity.
 * 
 * This class represents file attachments such as profile images. It stores file
 * metadata and binary data in the database.
 * 
 * Features: - Stores file name, type, description - Stores file content as byte
 * array (LOB) - Associates attachment with a user - Supports file upload using
 * MultipartFile
 * 
 * Mapped to table: ST_ATTACHMENT
 * 
 * @author Abhishish Bhawsar
 */
@Entity
@Table(name = "ST_ATTACHMENT")
public class AttachmentDTO extends BaseDTO {

	@Column(name = "NAME", length = 100)
	protected String name = null;

	@Column(name = "TYPE", length = 100)
	protected String type = null;

	@Column(name = "DESCRIPTION", length = 500)
	protected String description = null;

	@Column(name = "USER_ID")
	protected Long userId = null;

	@Lob
	@Column(name = "DOC")
	private byte[] doc;

	public AttachmentDTO() {
	}

	public AttachmentDTO(MultipartFile file) {
		name = file.getOriginalFilename();
		type = file.getContentType();

		try {
			doc = file.getBytes();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getValue() {
		return null;
	}

	public byte[] getDoc() {
		return doc;
	}

	public void setDoc(byte[] doc) {
		this.doc = doc;
	}

	/**
	 * Returns unique key.
	 * 
	 * @return "userId"
	 */
	@Override
	public String getUniqueKey() {
		return "userId";
	}

	/**
	 * Returns unique value.
	 * 
	 * @return userId as string
	 */
	@Override
	public String getUniqueValue() {
		return userId.toString();
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns table name.
	 * 
	 * @return "ATTACHMENT"
	 */
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "ATTACHMENT";
	}
}