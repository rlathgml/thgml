package com.ssafy.happyhouse.service;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ssafy.happyhouse.util.UnAuthorizedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtServiceImpl implements JwtService {

	private static final String SALT = "HappyHouse";
	
	@Override
	public <T> String create(String key, T data, String subject) {
		return Jwts.builder()
						.setHeaderParam("typ", "JWT")
						.setHeaderParam("regDate", System.currentTimeMillis())
						.setSubject(subject)
						.claim(key, data)
						.signWith(SignatureAlgorithm.HS256, this.generateKey())
						.compact();
	}

	private byte[] generateKey() {
		byte[] key = null;
		
		try {
			key = SALT.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return key;
	}
	
	@Override
	public boolean isUsable(String jwt) {
		// TODO Auto-generated method stub
		System.out.println(">>>>>>>>>>>>" + jwt);
		try {
			System.out.println("TRY");
			Jws<Claims> claims = Jwts.parser()
								.setSigningKey(this.generateKey())
								.parseClaimsJws(jwt);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("CATCH");
			e.printStackTrace();
			throw new UnAuthorizedException();
		}
	}
	
	@Override
	public Map<String, Object> get(String key) {
		// TODO Auto-generated method stub
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String jwt = request.getHeader("Token");
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser()
						 .setSigningKey(SALT.getBytes("UTF-8"))
						 .parseClaimsJws(jwt);
		} catch (Exception e) {
			// TODO: handle exception
			throw new UnAuthorizedException();
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> value = (LinkedHashMap<String, Object>)claims.getBody().get(key); 
		return value;
	}
	
	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return (String)this.get("user").get("userId");
	}

}
