package com.tminions.app.controllers;

import com.tminions.app.models.TemplateColumnsModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TemplateControllerTest {

    String templateName = "Client Profile";

    @BeforeAll
    static void init() {
    	BaseController.initUnirest();
    }

    @Test
    @DisplayName("get all template names is successful")
	void testGetAllTemplateNames () {
        assertFalse(TemplateController.getAllTemplateNames().isEmpty());
	}

    @Test
    @DisplayName("get all template names is successful")
    void testGetTemplateColumns () {
        TemplateColumnsModel res = TemplateController.getTemplateColumns(templateName);
        assertFalse(res.get_columns().isEmpty());
        assertFalse(res.get_column_name_map().isEmpty());
    }
	
}
