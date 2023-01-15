package io.github.thatusualguy.chessonline.vm;

import androidx.annotation.NonNull;

public class User {
	@NonNull
	public Boolean Logged_in = false;
	public String Jwt;

	public String Username;
	public String Email;

	public User(){
		Logged_in = false;
	}

	public User(String username, String email, String jwt){
		Logged_in = true;
		Username = username;
		Email = email;
		Jwt = jwt;
	}
}
