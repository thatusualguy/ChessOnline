package io.github.thatusualguy.chessonline.models;

import androidx.annotation.NonNull;

public class User {
	@NonNull
	public Boolean Logged_in = false;
	public String Jwt;

	public User(){
		Logged_in = false;
	}

	public User(String jwt){
		Logged_in = true;
		Jwt = jwt;
	}
}