package com.rays.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rays.common.BaseServiceImpl;
import com.rays.common.UserContext;
import com.rays.dao.UserDAOInt;
import com.rays.dto.UserDTO;
import com.rays.email.EmailBuilder;
import com.rays.email.EmailMessage;
import com.rays.email.EmailServiceInt;

/**
 * Service implementation for User entity.
 * 
 * This class extends BaseServiceImpl to provide standard CRUD operations
 * for UserDTO using UserDAOInt.
 * 
 * Additional Features:
 * - User registration with email notification
 * - User authentication (login)
 * - Forgot password functionality with email
 * - Change password with email notification
 * 
 * Annotated with @Service to indicate service layer component.
 * Annotated with @Transactional to manage database transactions.
 * 
 * @author Abhishish Bhawsar
 */
@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<UserDTO, UserDAOInt> implements UserServiceInt {

	@Autowired
	private EmailServiceInt emailservice;
	
	/**
	 * Finds user by login ID.
	 * 
	 * @param login login ID
	 * @param userContext user context
	 * @return UserDTO object
	 */
	@Transactional(readOnly = true)
	public UserDTO findByLogin(String login, UserContext userContext) {
		return baseDao.findByUniqueKey("login", login, userContext);
	}

	/**
	 * Registers a new user and sends registration email.
	 * 
	 * @param dto user data
	 * @param userContext user context
	 * @return registered UserDTO
	 */
	@Override
	public UserDTO register(UserDTO dto, UserContext userContext) {
		baseDao.add(dto, userContext);

		HashMap<String, String> map = new HashMap<>();
		map.put("login", dto.getLogin());
		map.put("password", dto.getPassword());
		map.put("firstName", dto.getFirstName());

		EmailMessage msg = new EmailMessage();
		msg.setTo(dto.getLogin());
		msg.setSubject("User Registration Successful");
		msg.setMessage(EmailBuilder.getUserRegistrationMessage(map));
		msg.setMessageType(EmailMessage.HTML_MSG);

		emailservice.sendMail(msg);
		return dto;
	}

	/**
	 * Authenticates user based on login ID and password.
	 * 
	 * @param loginId login ID
	 * @param password password
	 * @return UserDTO if authenticated, otherwise null
	 */
	@Override
	public UserDTO authenticate(String login, String password) {

		UserDTO dto = findByLogin(login, null);

		if (dto != null) {
			UserContext userContext = new UserContext(dto);
			if (password.equals(dto.getPassword())) {
				dto.setLastLogin(new Timestamp((new Date()).getTime()));
				dto.setUnsucessfullLoginAttempt(0);
				update(dto, userContext);
				return dto;
			} else {
				dto.setUnsucessfullLoginAttempt(1 + dto.getUnsucessfullLoginAttempt());
				update(dto, userContext);
			}
		}
		return null;
	}

	/**
	 * Handles forgot password functionality.
	 * Sends password to user's email.
	 * 
	 * @param loginId login ID
	 * @return true if email sent, false if user not found
	 */
	@Override
	public boolean forgotPassword(String login) {

		UserDTO dto = findByLogin(login, null);

		if (dto == null) {
			return false;
		}

		HashMap<String, String> map = new HashMap<>();

		map.put("firstName", dto.getFirstName());
		map.put("lastName", dto.getLastName());
		map.put("login", dto.getLogin());
		map.put("password", dto.getPassword());

		String message = EmailBuilder.getForgetPasswordMessage(map);

		EmailMessage email = new EmailMessage();

		email.setTo(dto.getLogin());
		email.setSubject("Your Password has been forgotten.....");
		email.setMessage(message);
		email.setMessageType(EmailMessage.HTML_MSG);

		emailservice.sendMail(email);

		return true;
	}

	/**
	 * Changes user password and sends confirmation email.
	 * 
	 * @param loginId     login ID
	 * @param oldPassword old password
	 * @param newPassword new password
	 * @param userContext user context
	 * @return updated UserDTO if successful, otherwise null
	 */
	@Override
	public UserDTO changePassword(String login, String oldPassword, String newPassword, UserContext userContext) {

		UserDTO dto = findByLogin(login, null);

		dto.setCreatedBy(userContext.getLogin());

		if (dto != null && oldPassword.equals(dto.getPassword())) {

			dto.setPassword(newPassword);
			update(dto, userContext);

			HashMap<String, String> map = new HashMap<>();

			map.put("firstName", dto.getFirstName());
			map.put("lastName", dto.getLastName());
			map.put("login", dto.getLogin());
			map.put("password", dto.getPassword());

			String message = EmailBuilder.getChangePasswordMessage(map);

			EmailMessage email = new EmailMessage();
			email.setTo(dto.getLogin());
			email.setSubject("ORS Password Changed Successfully");
			email.setMessage(message);
			email.setMessageType(EmailMessage.HTML_MSG);

			emailservice.sendMail(email);

			return dto;

		} else {
			return null;
		}
	}
}