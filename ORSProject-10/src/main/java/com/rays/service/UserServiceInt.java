package com.rays.service;

import com.rays.common.BaseServiceInt;
import com.rays.common.UserContext;
import com.rays.dto.UserDTO;

/**
 * Service interface for User entity.
 * 
 * This interface extends BaseServiceInt to provide standard CRUD operations for
 * UserDTO along with user-specific business functionalities.
 * 
 * Features: - Find user by login ID - User registration - User authentication
 * (login) - Forgot password functionality - Change password functionality
 * 
 * @author Abhishish Bhawsar
 */
public interface UserServiceInt extends BaseServiceInt<UserDTO> {

	/**
	 * Finds user by login ID.
	 * 
	 * @param name        login ID
	 * @param userContext user context
	 * @return UserDTO object
	 */
	public UserDTO findByLogin(String name, UserContext userContext);

	/**
	 * Registers a new user.
	 * 
	 * @param dto         user data
	 * @param userContext user context
	 * @return registered UserDTO
	 */
	public UserDTO register(UserDTO dto, UserContext userContext);

	/**
	 * Authenticates user credentials.
	 * 
	 * @param loginId  login ID
	 * @param password password
	 * @return UserDTO if authenticated, otherwise null
	 */
	public UserDTO authenticate(String login, String password);

	/**
	 * Handles forgot password functionality.
	 * 
	 * @param loginId login ID
	 * @return true if successful, false otherwise
	 */
	public boolean forgotPassword(String login);

	/**
	 * Changes user password.
	 * 
	 * @param loginId     login ID
	 * @param oldPassword old password
	 * @param newPassword new password
	 * @param userContext user context
	 * @return updated UserDTO if successful, otherwise null
	 */
	public UserDTO changePassword(String login, String oldPassword, String newPassword, UserContext userContext);

}
