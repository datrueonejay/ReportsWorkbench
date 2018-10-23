package com.tminions.app.fileParsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.tminions.app.clientRecord.ClientRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ExcelParserTest {

    private static File excelFile = new File("src/test/resources/iCare_template.xlsx");
    private static final String expetedString = "Processing Details|Update Record ID|Unique Identifier|Unique Identifier Value|Date of Birth (YYYY-MM-DD)|Postal Code where the service was received|Start Date of Service (YYYY-MM-DD)|Language of Service|Official Language of Preference|Type of Institution/Organization Where Client Received Services|Referred By|Services Received|Total Length of Orientation|Total Length of Orientation: Hours|Total Length of Orientation: Minutes|Number of Clients in Group|Directed at a specific Target Group|Target Group: Children (0-14 yrs)|Target Group: Youth (15-24 yrs)|Target Group: Seniors|Target Group: Gender-specific|Target Group: Refugees|Target Group: Ethnic/cultural/linguistic group|Target Group: Deaf or Hard of Hearing|Target Group: Blind or Partially Sighted|Target Group: Lesbian, Gay, Bisexual, Transgender, Queer (LGBTQ)|Target Group: Families/Parents|Target Group: Clients with other impairments (physical, mental)|Target Group: Clients with international training in a regulated profession|Target Group: Clients with international training in a regulated trade|Target Group: Official Language minorities|Overview of Canada|Overview of Canada Referrals|Sources of Information|Sources of Information Referrals|Rights and Freedoms|Rights and Freedoms Referrals|Canadian Law and Justice|Canadian Law and Justice Referrals|Important Documents|Important Documents Referrals|Improving English or French|Improving English or French Referrals|Employment and Income|Employment and Income Referrals|Education|Education Referrals|Housing|Housing Referrals|Health|Health Referrals|Money and Finances|Money and Finances Referrals|Transportation|Transportation Referrals|Communications and Media|Communications and Media Referrals|Community Engagement|Community Engagement Referrals|Becoming a Canadian Citizen|Becoming a Canadian Citizen Referrals|Interpersonal Conflict|Interpersonal Conflict Referrals|Was Essential Skills and Aptitude Training Received as Part of this Service?|Computer skills|Document Use|Interpersonal Skills and Workplace Culture|Leadership Training|Numeracy|Was Life Skills or Responsibilities of Citizenship Information Received as Part of this Service?|Life Skills|Rights and Responsibilities of Citizenship (based on discover Canada)|Support Services Received|Care for Newcomer Children|Child 1: Age|Child 1: Type of Care|Child 2: Age|Child 2: Type of Care|Child 3: Age|Child 3: Type of Care|Child 4: Age|Child 4: Type of Care|Child 5: Age|Child 5: Type of Care|Transportation|Provisions for Disabilities|Translation|Between|And|Interpretation|Between|And|Crisis Counselling|End Date of Service (YYYY-MM-DD)|Reason for update|";
    private static final ArrayList<String> expected = new ArrayList<>(95);
    private static ExcelParser ep;

    @BeforeAll
    static void init() throws IOException{

        ep = new ExcelParser(excelFile);
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

    //TODO: add more tests

}
