package com.tminions.app.fileParsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.tminions.app.Normalizer.DateVerifier;
import com.tminions.app.Normalizer.Normalizer;
import com.tminions.app.Normalizer.PhoneVerifier;
import com.tminions.app.Normalizer.PostalCodeVerifier;
import com.tminions.app.clientRecord.ClientRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ExcelParserTest {

    private static File excelFile = new File("src/test/resources/iCARE_template.xlsx");
    private static File excelFileBadFormat = new File("src/test/resources/iCARE_template_bad_formats.xlsx");
    private static final String expetedString = "processing_details|update_record_id|client_validation_type_id|client_validation_id|client_birth_dt|postal_cd|start_dttm|service_language_id|service_official_language_id|institution_type_id|service_referred_by_id|orientation_service_id|orientation_length_id|orientation_length_hours_no|orientation_length_minutes_no|group_clients_no_id|target_group_specific_ind|target_group_children_ind|target_group_youth_ind|target_group_senior_ind|target_group_gender_ind|target_group_refugee_ind|target_group_ethnic_ind|target_group_hearing_ind|target_group_visual_ind|target_group_LGBTQ_ind|target_group_families_parents_ind|target_group_other_impairments_ind|target_group_CWIT_in_regulated_profession_ind|target_group_CWIT_in_regulated_trade_ind|target_group_official_language_minorities_ind|topic_overview_given_ind|topic_overview_referral_ind|topic_information_given_ind|topic_information_referral_ind|topic_rights_given_ind|topic_rights_referral_ind|topic_law_given_ind|topic_law_referral_ind|topic_documents_given_ind|topic_documents_referral_ind|topic_language_given_ind|topic_language_referral_ind|topic_income_given_ind|topic_income_referral_ind|topic_education_given_ind|topic_education_referral_ind|topic_housing_given_ind|topic_housing_referral_ind|topic_health_given_ind|topic_health_referral_ind|topic_money_given_ind|topic_money_referral_ind|topic_transportation_given_ind|topic_transportation_referral_ind|topic_media_given_ind|topic_media_referral_ind|topic_community_given_ind|topic_community_referral_ind|topic_citizenship_given_ind|topic_citizenship_referral_ind|topic_conflict_given_ind|topic_conflict_referral_ind|training_received_service_ind|training_received_computer_ind|training_received_document_ind|training_received_interpersonal_ind|training_received_leadership_ind|training_received_numeracy_ind|skill_received_service_ind|essential_skill_life_ind|essential_skill_responsabilities_ind|support_received_ind|childminding_ind|childminding_NewcomerChildren[1]childminding_age_id|childminding_NewcomerChildren[1]childminding_type_id|childminding_NewcomerChildren[2]childminding_age_id|childminding_NewcomerChildren[2]childminding_type_id|childminding_NewcomerChildren[3]childminding_age_id|childminding_NewcomerChildren[3]childminding_type_id|childminding_NewcomerChildren[4]childminding_age_id|childminding_NewcomerChildren[4]childminding_type_id|childminding_NewcomerChildren[5]childminding_age_id|childminding_NewcomerChildren[5]childminding_type_id|transportation_ind|support_disability_ind|translation_ind|translation_language_from_id|translation_language_to_id|interpretation_ind|interpretation_language_from_id|interpretation_language_to_id|counselling_ind|end_dttm|assessment_update_reason_id";
    private static final ArrayList<String> expected = new ArrayList<>(95);
    private static ExcelParser ep;
    private static ExcelParser epBadFormat;

    @BeforeAll
    static void init() throws IOException{
        ep = new ExcelParser(excelFile);
        epBadFormat = new ExcelParser(excelFileBadFormat);
        Normalizer.register(new DateVerifier());
        Normalizer.register(new PhoneVerifier());
        Normalizer.register(new PostalCodeVerifier());
    }


    @Test
    @DisplayName("test setColumnHeaders works")
    void setColumnHeadersTest() throws IOException {
        expected.addAll(Arrays.asList(expetedString.split("\\|")));

        ep.setColumnHeaders(ep.getWb().getSheetAt(0));

        assertEquals(expected, ep.getColumnHeaders());

    }

    @Test
    @DisplayName("test parseClients works")
    void getCLients() throws IOException {
        ArrayList<ClientRecord> clients = ep.parseClients();

        assertTrue(clients.size() == 1);

        //TODO: implement test

    }
    
    @Test
    @DisplayName("test Normalizers work")
    void testNormalizersWithSheet() throws IOException {
    	//read all clients in badFormat excel
    	epBadFormat.setColumnHeaders(epBadFormat.getWb().getSheetAt(0));
        ArrayList<ClientRecord> clients = epBadFormat.parseClients();
    	
        //make sure all inval clients have valid=false, bad clients don't have inalid_rows key 
        for (ClientRecord client : clients) {
        	
        	if (client.getUniqueID().contains("inval")) {
        		HashMap<String, String> data = client.getData();
        		assertTrue(data.get("valid").equalsIgnoreCase("false"),
                        client.getUniqueID() + " should have valid = false");
        	} else if (client.getUniqueID().contains("bad")) {
        		HashMap<String, String> data = client.getData();
        		assertTrue(data.get("valid").equalsIgnoreCase("true"),
                        client.getUniqueID() + " should have valid = true");
        	}
        }

        //TODO: implement more thorough test!

    }


    //TODO: add more tests

}
