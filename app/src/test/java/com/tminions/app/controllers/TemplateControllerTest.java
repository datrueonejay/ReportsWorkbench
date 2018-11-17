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

    String templateName = "Test Template";
    Map<String, String> templateColumns = new HashMap<String, String>() {
        {
            put("test1", "dataType1");
            put("test2", "dataType2");
            put("test3", "dataType3");
        }
    };
    Map<String, String> templateMap = new HashMap<String, String>() {
        {
            put("test1", "test 1");
            put("test2", "test 2");
            put("test3", "test 3");
        }
    };
    List<String> mandatoryColumns = Arrays.asList("test1", "test3");

    @BeforeAll
    static void init() {
    	BaseController.initUnirest();
    }

    //TODO: Uncomment @Test and verify integration test works
    //@Test
    @DisplayName("get all template names is successful")
	void testGetAllTemplateNames () {
        assertFalse(TemplateController.getAllTemplateNames().isEmpty());
	}

	//TODO: Uncomment @Test and verify integration test works
    //@Test
    @DisplayName("get all template names is successful")
    void testGetTemplateColumns () {
        TemplateColumnsModel res = TemplateController.getTemplateColumns(templateName);
        // Check template columns match
        assertEquals(templateColumns, res.get_columns());
        assertEquals(mandatoryColumns, res.get_mandatory_columns());
        assertEquals(templateMap, res.get_column_name_map());
    }
	
}
