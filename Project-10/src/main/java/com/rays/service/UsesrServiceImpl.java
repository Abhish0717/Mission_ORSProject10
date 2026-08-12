package com.rays.service;

import com.rays.common.BaseServiceImpl;
import com.rays.common.UserContext;
import com.rays.dao.UserDAOInt;
import com.rays.dto.UserDTO;

public class UsesrServiceImpl extends BaseServiceImpl<UserDTO, UserDAOInt> implements UserServiceInt {

	@Override
	public UserDTO findByLoginId(String name, UserContext userContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO register(UserDTO dto, UserContext userContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO authenticate(String loginId, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO forgotPassword(String loginId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO changePassword(String loginId, String oldPassword, String newPassword, UserContext userContext) {
		// TODO Auto-generated method stub
		return null;
	}

}
