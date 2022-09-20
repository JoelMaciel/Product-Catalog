package com.joel.catalog.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserInsertDTO extends UserDTO{
	
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
