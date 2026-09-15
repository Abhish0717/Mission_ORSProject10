package com.rays.common;

/**
 * UserContextHolder is a utility class that manages UserContext
 * using ThreadLocal storage.
 * 
 * This ensures that each request/thread has its own separate
 * UserContext, avoiding conflicts in multi-threaded environments.
 * 
 * Commonly used in web applications to store user session data
 * per request lifecycle.
 * 
 * Responsibilities:
 * - Set UserContext for current thread
 * - Get UserContext for current thread
 * - Clear UserContext after request completion
 * 
 * @author Abhishish Bhawsar
 */
public class UserContextHolder {

	/**
	 * ThreadLocal variable to store UserContext per thread.
	 */
	private static final ThreadLocal<UserContext> threadLocal = new ThreadLocal<>();

	/**
	 * Sets UserContext for the current thread.
	 * 
	 * @param context UserContext object
	 */
	public static void setContext(UserContext context) {
		threadLocal.set(context);
	}

	/**
	 * Gets UserContext of the current thread.
	 * 
	 * @return UserContext object
	 */
	public static UserContext getContext() {
		return threadLocal.get();
	}

	/**
	 * Clears UserContext from the current thread.
	 * This is important to prevent memory leaks.
	 */
	public static void clear() {
		threadLocal.remove();
	}
}