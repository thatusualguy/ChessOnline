package io.github.thatusualguy.chessonline.models;

public class UserInfo {
	public String username;
	public String email;
	public String password;



	public UserInfo(String username, String email) {
		this.username = username;
		this.email = email;
	}

	public UserInfo(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}
}
