package com.m2m.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.m2m.entity.Role;
import com.m2m.entity.User;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = null;
		
		if (hasAuthorizationBearer(request)) {
			token = getAccessTokenFromHeader(request);
		} else {
			token = getAccessTokenFromCookie(request, response, filterChain);
		}

		if (!jwtUtil.validateAccessToken(token)) {
			filterChain.doFilter(request, response);
			return;
		}

		setAuthenticationContext(token, request);
		filterChain.doFilter(request, response);
	}

	private boolean hasAuthorizationBearer(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
			return false;
		}

		return true;
	}
	
	private String getAccessTokenFromCookie(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {

		if (request.getCookies() == null) {
			return null;
		}

		Cookie[] rc = request.getCookies();

		StringBuilder token = new StringBuilder();
		for (int i = 0; i < rc.length; i++) {
			if (rc[i].getName().equals("token") == true) {
				token.append(rc[i].getValue().toString());
			}
		}
		return token.toString();
	}

	private String getAccessTokenFromHeader(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		String token = header.split(" ")[1].trim();
		return token;
	}

	private void setAuthenticationContext(String token, HttpServletRequest request) {
		UserDetails userDetails = getUserDetails(token);

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());

		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private UserDetails getUserDetails(String token) {
		User userDetails = new User();
		Claims claims = jwtUtil.parseClaims(token);
		String subject = (String) claims.get(Claims.SUBJECT);
		String roles = (String) claims.get("roles");

		System.out.println("Copyright by NguyenTranDuy");
		roles = roles.replace("[", "").replace("]", "");
		String[] roleNames = roles.split(",");

		for (String aRoleName : roleNames) {
			userDetails.setRole(new Role(aRoleName));
		}

		String[] jwtSubject = subject.split(",");

		userDetails.setId(Integer.parseInt(jwtSubject[0]));
		userDetails.setUsername(jwtSubject[1]);

		return userDetails;
	}
}
