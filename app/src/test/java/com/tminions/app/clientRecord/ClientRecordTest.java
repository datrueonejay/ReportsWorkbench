package com.tminions.app.clientRecord;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class ClientRecordTest {

    private static HashMap<String, String> data0 = new HashMap<String, String>();
    private static HashMap<String, String> data1 = new HashMap<String, String>();
    private static HashMap<String, String> data2 = new HashMap<String, String>();
    private static HashMap<String, String> data3 = new HashMap<String, String>();
    private static HashMap<String, String> data4 = new HashMap<String, String>();

    @BeforeAll
    static void init() {
        data0.put("Unique Identifier", "FOSS/GCMS Client ID");
        data0.put("Unique Identifier value", "12345678");
        data0.put("Date of Birth (YYYY-MM-DD)", "1978-05-20");

        data1.put("Update record ID", "10387104");
        data1.put("Course Code", "L-CCSMAR18008");

        data2.putAll(data0);
        data2.putAll(data1);

        data3.put("Date of Birth (YYYY-MM-DD)", "1978-05-20");
        data3.put("Course Code", "L-CCSMAR18008");

        data4.put("Date of Birth (YYYY-MM-DD)", "1978-05-20");

    }


    @Test
    @DisplayName("test toJson for dummy client with unique identifier value")
    void testToJsonHasUniqueValue() {
        ClientRecord client0 = new ClientRecord(data0);
        String json = client0.toJson();
        assertTrue(json.startsWith("\"12345678\" : {"));
        // Must test one line at a time because order of lines is not guaranteed
        assertTrue(json.contains("\t\"Unique Identifier value\" : \"12345678\""));
        assertTrue(json.contains("\t\"Date of Birth (YYYY-MM-DD)\" : \"1978-05-20\""));
        assertTrue(json.contains("\t\"Unique Identifier\" : \"FOSS/GCMS Client ID\""));

    }
    @Test
    @DisplayName("test toJson for dummy client without unique identifier value" +
            "but with update record ID")
    void testToJsonHasRecordId() {
        ClientRecord client = new ClientRecord(data1);
        String json = client.toJson();
        assertTrue(json.startsWith("\"10387104\" : {"));
        assertTrue(json.contains("\t\"Update record ID\" : \"10387104\""));
        assertTrue(json.contains("\t\"Course Code\" : \"L-CCSMAR18008\""));

    }

    @Test
    @DisplayName("test toJson for dummy client with unique identifier value" +
            "AND update record ID")
    void testToJsonHasBoth() {
        ClientRecord client = new ClientRecord(data2);
        String json = client.toJson();
        assertTrue(json.startsWith("\"12345678\" : {"));
        assertTrue(json.contains("\t\"Update record ID\" : \"10387104\""));
        assertTrue(json.contains("\t\"Course Code\" : \"L-CCSMAR18008\""));
        assertTrue(json.contains("\t\"Unique Identifier value\" : \"12345678\""));
        assertTrue(json.contains("\t\"Date of Birth (YYYY-MM-DD)\" : \"1978-05-20\""));
        assertTrue(json.contains("\t\"Unique Identifier\" : \"FOSS/GCMS Client ID\""));

    }

    @Test
    @DisplayName("test toJson for dummy client without unique identifier value" +
            "and without update record ID")
    void testToJsonHasNoIdentifierOrRecordId() {
        ClientRecord client = new ClientRecord(data3);
        String json = client.toJson();
        assertTrue(json.contains("\t\"Date of Birth (YYYY-MM-DD)\" : \"1978-05-20\""));
        assertTrue(json.contains("\t\"Course Code\" : \"L-CCSMAR18008\""));
    }

    @Test
    @DisplayName("test toJson for dummy client one line")
    void testToJsonHasOneLine() {
        ClientRecord client = new ClientRecord(data4);
        String json = client.toJson();
        assertTrue(json.contains("\t\"Date of Birth (YYYY-MM-DD)\" : \"1978-05-20\"\n"));
    }
}
