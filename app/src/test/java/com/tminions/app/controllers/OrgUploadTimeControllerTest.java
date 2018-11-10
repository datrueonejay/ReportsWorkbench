package com.tminions.app.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class OrgUploadTimeControllerTest {
	
    @BeforeAll
    static void init() {
    	BaseController.initUnirest();
    }
	
	@Test
    @DisplayName("test get upload time is successful")
	void testLoginValid () {
		assertFalse(OrgUploadTimeController.getUploadTimes().isEmpty());
	}
	

}
