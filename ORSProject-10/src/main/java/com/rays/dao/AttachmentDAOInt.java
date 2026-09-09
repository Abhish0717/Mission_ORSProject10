package com.rays.dao;

import com.rays.common.BaseDAOInt;
import com.rays.dto.AttachmentDTO;

/**
 * DAO Interface for Attachment entity.
 * 
 * This interface extends BaseDAOInt to inherit
 * generic CRUD operations such as:
 * - Add
 * - Update
 * - Delete
 * - Find by ID
 * - Search
 * 
 * It can be extended further to define
 * custom Attachment-specific database operations.
 * 
 * @author Abhishish Bhawsar
 */
public interface AttachmentDAOInt extends BaseDAOInt<AttachmentDTO> {

}