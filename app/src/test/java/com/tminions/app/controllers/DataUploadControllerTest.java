package com.tminions.app.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataUploadControllerTest {
	
    @BeforeAll
    static void init() {
    	BaseController.initUnirest();
    }

    String validUsername = "admin@teq.com";
    String testJson = "\"Upload Integration Test Template\" : { " +
            "\"ROW 1\" : { " +
            "\"Col 1\" : \"Val 1\", " +
            "\"Col 2\" : \"Val 2\"}," +
            "\"ROW 2\" : {" +
            "\"Col 1\" : \" Val 1\"," +
            "\"Col 2\" : \"Val 2\"} " +
            "}";

	@Test
    @DisplayName("test upload data is successful")
	void testLoginValid () {
        HttpResponse<JsonNode> res = DataUploadController.uploadData(validUsername, testJson);
        assertEquals(200, res.getStatus());
	}
	

}
