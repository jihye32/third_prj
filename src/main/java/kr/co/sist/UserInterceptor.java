package kr.co.sist;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class UserInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		String ctx = request.getContextPath();
		if(uri.matches("^" + ctx + "/product/\\d+")) {
			return true;
		}
			
		HttpSession session = request.getSession(false);
	    if (session == null || session.getAttribute("uid") == null) {
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
	        return false;
	    }
		
		return true;
	}
	
}
