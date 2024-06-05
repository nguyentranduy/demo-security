package com.m2m.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.m2m.dto.AuthRequest;
import com.m2m.entity.User;
import com.m2m.jwt.JwtTokenUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

	@Autowired
	AuthenticationManager authManager;

	@Autowired
	JwtTokenUtil jwtUtil;

	@GetMapping("/index")
	public String doGetIndex(Model model) {
		model.addAttribute(new AuthRequest());
		return "index";
	}

	@PostMapping("/login")
	public String doGetIndex(@ModelAttribute AuthRequest authRequest, HttpServletResponse servletResponse) {
		try {
			Authentication authentication = authManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

			User user = (User) authentication.getPrincipal();
			String accessToken = jwtUtil.generateAccessToken(user);
			Cookie cookie = new Cookie("token", accessToken);
			cookie.setMaxAge(60*60); // 3600s ~ 1h
			servletResponse.addCookie(cookie);

			return "succeed";
		} catch (BadCredentialsException ex) {
			return "redirect:/index";
		}
	}
}
