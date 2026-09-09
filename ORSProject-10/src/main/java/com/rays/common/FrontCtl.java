package com.rays.common;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Front Controller Interceptor for handling pre-request processing.
 * 
 * This interceptor is responsible for: - Handling CORS (Cross-Origin Resource
 * Sharing) headers - Managing preflight (OPTIONS) requests - Validating user
 * session before processing requests
 * 
 * If session is not found, it blocks the request and returns an unauthorized
 * response.
 * 
 * Annotated with @Component so it can be registered as a Spring Bean.
 * 
 * @author Abhishish Bhawsar
 */
@Component
public class FrontCtl extends HandlerInterceptorAdapter {

	/**
	 * Intercepts incoming HTTP requests before they reach the controller.
	 * 
	 * Responsibilities: - Set CORS headers - Handle OPTIONS preflight requests -
	 * Validate user session
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @param handler  handler object
	 * @return true if request should proceed, false otherwise
	 * @throws Exception in case of errors
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// Set CORS headers
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Allow-Credentials", "true");

		// Handle preflight request
		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
			return false;
		}

		// Get session
		HttpSession session = request.getSession();

		// Validate user session
		if ((UserContext) session.getAttribute("userContext") == null) {
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			PrintWriter out = response.getWriter();
			out.print("{\"success\":\"false\",\"error\":\"OOPS! Your session has been expired\"}");
			out.close();
			return false;
		}
		return true;
	}
}