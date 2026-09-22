package com.rays.ctl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rays.common.BaseCtl;
import com.rays.common.ORSResponse;
import com.rays.common.UserContext;
import com.rays.config.JWTUtil;
import com.rays.dto.UserDTO;
import com.rays.form.ForgetPasswordForm;
import com.rays.form.LoginForm;
import com.rays.form.UserForm;
import com.rays.form.UserRegistrationForm;
import com.rays.service.UserServiceInt;

/**
 * Authentication Controller handling login, signup, logout,
 * and password recovery functionalities.
 * 
 * Endpoints:
 * - /Auth/login
 * - /Auth/signUp
 * - /Auth/logout
 * - /Auth/forgetPassword
 * 
 * Uses JWT for authentication and BaseCtl for validation handling.
 * 
 * @author Abhishish Bhawsar
 */
@RestController
@RequestMapping(value = "Auth")
public class LoginCtl extends BaseCtl<UserForm, UserDTO, UserServiceInt> {
	
	/**
	 * JWT utility for token generation and validation.
	 */
	@Autowired
	private JWTUtil jwtUtil;
	
	/**
	 * User service for user-related operations.
	 */
	@Autowired
	private UserServiceInt userService;

	/**
	 * Handles user login and generates JWT token.
	 * 
	 * @param form login form data
	 * @param bindingResult validation result
	 * @param session HTTP session
	 * @param request HTTP request
	 * @return ORSResponse containing user details and token
	 * @throws Exception in case of errors
	 */
	@PostMapping("login")
	public ORSResponse login(@RequestBody @Valid LoginForm form, BindingResult bindingResult, HttpSession session,
			HttpServletRequest request) throws Exception {

		ORSResponse res = validate(bindingResult);

		if (!res.isSuccess()) {
			return res;
		}

		UserDTO dto = baseService.authenticate(form.getLogin(), form.getPassword());

		if (dto == null) {
			res.setSuccess(false);
			res.addMessage("Invalid Login ID & Password");
		} else {

			UserContext context = new UserContext(dto);

			String token = jwtUtil.generateToken(dto.getId(), dto.getLogin(), dto.getRoleName());

			res.setSuccess(true);
			res.addData(dto);
			res.addResult("loginId", dto.getLogin());
			res.addResult("role", dto.getRoleName());
			res.addResult("fname", dto.getFirstName());
			res.addResult("lname", dto.getLastName());
			res.addResult("token", token);
			return res;
		}
		return res;
	}

	/**
	 * Handles user registration (signup).
	 * 
	 * @param form registration form data
	 * @param bindingResult validation result
	 * @return ORSResponse with registration status
	 */
	@PostMapping("signUp")
	public ORSResponse signUp(@RequestBody @Valid UserRegistrationForm form, BindingResult bindingResult) {

		ORSResponse res = validate(bindingResult);

		if (!res.isSuccess()) {
			return res;
		}

		UserDTO dto = baseService.findByLogin(form.getLogin(), userContext);

		if (dto != null) {
			res.setSuccess(false);
			res.addMessage("Login Id already exists");
			return res;
		}

		dto = new UserDTO();
		dto.setFirstName(form.getFirstName());
		dto.setLastName(form.getLastName());
		dto.setLogin(form.getLogin());
		dto.setPassword(form.getPassword());
		dto.setDob(form.getDob());
		dto.setGender(form.getGender());
		dto.setPhone(form.getPhone());

		dto.setStatus("Inactive");
		dto.setRoleId(2L);

		baseService.register(dto, userContext);

		res.setSuccess(true);
		res.addMessage("User has been registered successfully..!!");
		return res;
	}

	/**
	 * Handles user logout by invalidating session.
	 * 
	 * @param session HTTP session
	 * @return ORSResponse with logout message
	 * @throws Exception in case of errors
	 */
	@GetMapping("logout")
	public ORSResponse logout(HttpSession session) throws Exception {

		ORSResponse res = new ORSResponse();

		session.invalidate();

		res.addMessage("Logout successfully..!!");

		return res;
	}

	/**
	 * Handles forgot password functionality.
	 * Sends password to registered email.
	 * 
	 * @param form forget password form
	 * @param bindingResult validation result
	 * @return ORSResponse with operation status
	 */
	@PostMapping("forgetPassword")
	public ORSResponse forgetPassword(@RequestBody @Valid ForgetPasswordForm form, BindingResult bindingResult) {
		ORSResponse res = validate(bindingResult);

		if (!res.isSuccess()) {
			return res;
		}

		boolean flag = userService.forgotPassword(form.getLogin());

		if (flag == true) {
			res.setSuccess(true);
			res.addMessage("Password sent to your email");
		} else {
			res.setSuccess(false);
			res.addMessage("Login Id not found");
		}

		return res;
	}
}