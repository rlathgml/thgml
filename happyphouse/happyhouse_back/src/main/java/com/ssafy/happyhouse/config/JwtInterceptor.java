package com.ssafy.happyhouse.config;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ssafy.happyhouse.service.JwtService;
import com.ssafy.happyhouse.util.UnAuthorizedException;

@Component
public class JwtInterceptor implements HandlerInterceptor {
	private static final String HEADER_AUTH = "Authorization";
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
//		System.out.println("PREHANDLE-------------------------------------------");
//		Enumeration<String> e = request.getHeaderNames();
//		while(e.hasMoreElements()) {
//			String ele = e.nextElement();
//			System.out.println(ele + " : " + request.getHeader(ele));
//		}
//		System.out.println("PREHANDLE-------------------------------------------");
//		System.out.println();
//		final String token = request.getHeader(HEADER_AUTH);
//		System.out.println("JwtInterceptor >>>>>>>>>>>> " + token);
		//if(token != null && jwtService.isUsable(token))
			return true;
		//else
		//	throw new UnAuthorizedException();
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
//		System.out.println("POSTHANDLE-------------------------------------------");
//		Enumeration<String> e = request.getHeaderNames();
//		while(e.hasMoreElements()) {
//			String ele = e.nextElement();
//			System.out.println(ele + " : " + request.getHeader(ele));
//		}
	}
}
