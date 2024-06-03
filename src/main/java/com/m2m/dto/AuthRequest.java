package com.m2m.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthRequest implements Serializable {
	
	private static final long serialVersionUID = -4473169199655220400L;

	public String username;
	public String password;
}
