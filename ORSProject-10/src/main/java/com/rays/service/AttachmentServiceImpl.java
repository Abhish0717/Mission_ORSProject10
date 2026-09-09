package com.rays.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rays.common.BaseServiceImpl;
import com.rays.dao.AttachmentDAOInt;
import com.rays.dto.AttachmentDTO;

/**
 * Service implementation for Attachment entity.
 * 
 * This class extends BaseServiceImpl to provide standard CRUD operations for
 * AttachmentDTO using AttachmentDAOInt.
 * 
 * Features: - Handles attachment-related business logic - Supports
 * transactional operations - Inherits generic service methods like add, update,
 * delete, search, etc.
 * 
 * Annotated with @Service to indicate service layer component. Annotated
 * with @Transactional to manage database transactions.
 * 
 * @author Abhishish Bhawsar
 */
@Service
@Transactional
public class AttachmentServiceImpl extends BaseServiceImpl<AttachmentDTO, AttachmentDAOInt>
		implements AttachmentServiceInt {

}