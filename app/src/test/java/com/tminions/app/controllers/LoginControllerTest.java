package com.tminions.app.controllers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.tminions.app.models.LoginModel;

public class LoginControllerTest {
	
	LoginModel valid_user = new LoginModel("admin@teq.com", "pass1");
	LoginModel invalid_user = new LoginModel("hackerman@hack.com", "password");
	
    @BeforeAll
    static void init() {
    	LoginController.initUnirest();
    }
	
	@Test
    @DisplayName("test login valid")
	void testLoginValid () {
		Boolean loginSuccess = LoginController.login(valid_user);
		assertTrue(loginSuccess);
	}
	
	@Test
    @DisplayName("test login invalid")
	void testLoginInvalid () {
		Boolean loginSuccess = LoginController.login(invalid_user);
		assertFalse(loginSuccess);
	}
	
}
