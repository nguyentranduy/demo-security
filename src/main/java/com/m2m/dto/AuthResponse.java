package com.m2m.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthResponse implements Serializable {
	
	private static final long serialVersionUID = -3695318231368220411L;

	private String username;
	private String accessToken;
}
