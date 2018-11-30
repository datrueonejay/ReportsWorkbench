package com.tminions.app.jsonMaker;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tminions.app.Normalizer.DateVerifier;
import com.tminions.app.Normalizer.Normalizer;
import com.tminions.app.Normalizer.PhoneVerifier;
import com.tminions.app.Normalizer.PostalCodeVerifier;
import com.tminions.app.clientRecord.ClientRecord;
import com.tminions.app.fileParsers.ExcelParser;
import com.tminions.app.models.ReportDataModel;
import com.tminions.app.models.TrendReportDataModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class JsonMaker {



    public static final String[] MONTHS = {"January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October",
            "November", "December"};
    public static final String[] AGE_GROUPS = {"0-9", "10-19", "20-29", "30-39", "40-49", "50-59", "60-69", "70-79", ">80"};
    public static final String BIRTH_DATE_IN_DATABASE = "client_birth_dt";
    public static final String SERVICE_START_DATE = "start_dt";
    public static final String DATABASE_DELIMETER = "_";
    public static final String QUERY_STRING_DELIMETER = " ";
    /**
     * returns a json String in the form
     * <tt>
     *     "templatename" : {
     *         "ClientRecordOne" : {
     *             "unique identifier" : "12345678",
     *             "date of birth" : "09-09-1998"
     *         },
     *         "ClientRecordTwo" : {
     *              "unique identifier" : "12345678",
     *              "date of birth" : "09-09-1998"
     *         }
     *     }
     * </tt>
     * @param templateName the key of the parent node (see above)
     * @return json String of all clients in clientRecords
     */
    public static String jsonFromClientList(List<ClientRecord>clients , String templateName) {

        //join all clients in json format
        StringJoiner clientJoiner = new StringJoiner(",\n", "", "\n");

        for (ClientRecord client : clients) {
            clientJoiner.add(client.toJson());
        }

        String json = clientJoiner.toString();

        //indent all lines
        json = json.replaceAll("\\n", "\n\t")
                .replaceAll("\\A", "\t");


        //need to insert beginning of json ("templateName" : {)
        json = json.replaceFirst("\\A", "\"" + templateName + "\" : {\n")
                .replaceFirst("\\z", "\n}");

        // and end (})

        return json;

    }

    /**
     * Returns a list of all clients in the files <tt>files</tt>
     * @param files list of files to get the clients from, currently only supports excel files
     * @return list<ClientRecord> of clientRecords in <tt>files</tt>
     */
    public static List<ClientRecord> clientListFromFileList(List<File> files) throws IOException {
        ArrayList<ClientRecord> clients = new ArrayList<>();
        // TODO: in the future add functionality for distinguishing between CSV and excel files

        // Register all the verifiers
        Normalizer.register(new PhoneVerifier());
        Normalizer.register(new DateVerifier());
        Normalizer.register(new PostalCodeVerifier());


        // Add all the clients from each file to the running list clients
        for (File file: files) {
            ExcelParser ep = new ExcelParser(file);
            clients.addAll(ep.parseClients());
        }

        return clients;
    }


    public static HashMap<String, Integer> initializeHashMap(HashMap<String, Integer> dataHashMap, String[] keySet)
    {

        for(int i = 0; i < keySet.length; i++)
        {
            dataHashMap.put(keySet[i], 0);
        }

        return dataHashMap;
    }

    public static TrendReportDataModel generateTrendDataFromJSON(String jsonString, String trendType, String trendValue, String templateName)
    {
        String closestColumnValue = "";
        HashMap<String, Integer> trendReportData = new HashMap<>();

        String[] categories = AGE_GROUPS;
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonString);

        if(trendType.equals("By Age"))
        {
            trendReportData = initializeHashMap(trendReportData, AGE_GROUPS);
            categories = AGE_GROUPS;
        }
        else if(trendType.equals("By Month"))
        {
            trendReportData = initializeHashMap(trendReportData, MONTHS);
            categories = MONTHS;
        }

        if(element.isJsonObject())
        {
            JsonObject serverData = element.getAsJsonObject();
            JsonElement allData = serverData.get("data");
            String allDataCorrectFormat = allData.toString().replace("\\", "");
            allDataCorrectFormat =  allDataCorrectFormat.substring(1, allDataCorrectFormat.length()-1);

            JSONArray jsonArray = new JSONArray(allDataCorrectFormat);

            closestColumnValue = getClosestColumnValue(jsonArray.getJSONObject(0).toString(), trendValue);

            String assessmentStartColumnValue = checkIfSubstring(jsonArray.getJSONObject(0).toString(), SERVICE_START_DATE);

            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject personsData = jsonArray.getJSONObject(i);

                try
                {
                    String serviceWanted = personsData.get(closestColumnValue).toString();

                    if (trendType.equals("By Age")) {
                        String personDOB = personsData.get(BIRTH_DATE_IN_DATABASE).toString();

                        String personsAge = String.valueOf(calculateAge(personDOB));

                        String ageRange = getAgeRange(personsAge);

                        if (serviceWanted.equals("Yes")) {
                            int ageRangeValue = trendReportData.get(ageRange);
                            ageRangeValue += 1;
                            trendReportData.put(ageRange, ageRangeValue);
                        }
                    } else if (trendType.equals("By Month")) {
                        String personServiceStartDate = personsData.get(assessmentStartColumnValue).toString();

                        String monthOfService = getMonthOfServiceStart(personServiceStartDate);

                        if (serviceWanted.equals("Yes")) {
                            int usersInMonth = trendReportData.get(monthOfService);
                            usersInMonth += 1;
                            trendReportData.put(monthOfService, usersInMonth);
                        }
                    }
                }
                catch(JSONException jse)
                {
                    System.out.println("One or more of the documents were not included in the data due to not having the relevant column");
                }
                catch(Exception e)
                {
                    System.out.println("An unexpected error occurred during computation of trends data.");
                }
                finally
                {
                    continue;
                }
            }

        }

        return new TrendReportDataModel(categories, trendReportData, templateName, closestColumnValue);
    }


    public static String getMonthOfServiceStart(String dateOfServiceStart)
    {
        String[] dateTokens = dateOfServiceStart.split("-");

        String monthString;
        switch (dateTokens[1]) {
            case "01":  monthString = "January";
                break;
            case "02":  monthString = "February";
                break;
            case "03":  monthString = "March";
                break;
            case "04":  monthString = "April";
                break;
            case "05":  monthString = "May";
                break;
            case "06":  monthString = "June";
                break;
            case "07":  monthString = "July";
                break;
            case "08":  monthString = "August";
                break;
            case "09":  monthString = "September";
                break;
            case "10": monthString = "October";
                break;
            case "11": monthString = "November";
                break;
            case "12": monthString = "December";
                break;
            default: monthString = "Invalid month";
                break;
        }

        return monthString;
    }

    public static String getAgeRange(String personsAge)
    {
        char firstDigitOfPersonsAge = personsAge.charAt(0);

        int ageInIntegers = Integer.parseInt(personsAge);

        if(ageInIntegers > 80) {
            return ">80";
        }
        else if(personsAge.length() == 1)
        {
            return "0-9";
        }

        String firstValueOfRange = firstDigitOfPersonsAge + "0";

        String finalValueOfRange = firstDigitOfPersonsAge + "9";

        return firstValueOfRange + "-" + finalValueOfRange;
    }

    public static int calculateAge(String dateOfBirth)
    {
        String[] dobValues = dateOfBirth.split("-");


        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();

        int ageInYears = year - Integer.parseInt(dobValues[0]);

        int calculateMonths = month - Integer.parseInt(dobValues[1]);

        if(calculateMonths > 0)
        {
            return ageInYears;
        }

        return ageInYears - 1;
    }



    public static int distanceBetweenStrings(String a, String b)
    {
        a = a.toLowerCase();
        b = b.toLowerCase();
        // i == 0
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++)
        {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++)
            {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }


    public static int getNumberOfSubstrings(String str1, String str2, String delimeter1, String delimeter2)
    {
        String[] str1_components = str1.split(delimeter1);
        String[] str2_components = str2.split(delimeter2);

        int wordMatchCount = 0;
        for(int i = 0; i < str1_components.length; i++)
        {
            System.out.println("The current ith component is: " + str1_components[i]);
            for(int j = 0; j < str2_components.length; j++)
            {
                //System.out.println("The current jth component that it is being to compared to is: " + str2_components[j]);
                if(str1_components[i].equalsIgnoreCase(str2_components[j]))
                {
                    wordMatchCount++;
                }
                //System.out.println("The current word count is: " + String.valueOf(wordMatchCount));
            }
        }

        System.out.print("The word match count for these two words is: " + wordMatchCount);

        return wordMatchCount;
    }


    /**
     *
     */
    public static String getClosestColumnValue(String jsonString, String enteredColumnValue)
    {
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonString);
        JsonObject obj = element.getAsJsonObject();

        int lowestDistance = 10000;
        String matchingColumn = "";
        Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();
        for(Map.Entry<String, JsonElement> entry: entries)
        {
            int distanceBetweenStrings = distanceBetweenStrings(entry.getKey(), enteredColumnValue);
            int similarStringFactor = getNumberOfSubstrings(entry.getKey(), enteredColumnValue, DATABASE_DELIMETER, QUERY_STRING_DELIMETER);


            int newDistance = 0;
            if(similarStringFactor == 0) newDistance = distanceBetweenStrings * 2;
            else newDistance = distanceBetweenStrings / similarStringFactor;

            System.out.println("The distance between the strings was: " + String.valueOf(newDistance) + " for " + entry.getKey());

            if(newDistance < lowestDistance)
            {
                lowestDistance = newDistance;
                matchingColumn = entry.getKey();
                System.out.println("The new closest column value is: ");
            }
        }
        return matchingColumn;
    }


    public static String checkIfSubstring(String jsonString, String startDateString)
    {

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonString);
        JsonObject obj = element.getAsJsonObject();

        String matchingColumn = "";
        Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();
        for(Map.Entry<String, JsonElement> entry: entries)
        {
            if(entry.getKey().contains(startDateString))
            {
                matchingColumn = entry.getKey();
            }
        }
        return matchingColumn;
    }

    /**
     * Returns a RDM object so that the data can then be plotted in the graphs.
     * @param jsonString
     * @return ReportDataModel Object containing data from the server.
     */
    public static ReportDataModel convertJsonResponseToRDM(String jsonString)
    {
        String templateName;
        HashMap<String, String[][]> reportData = new HashMap<String, String[][]>();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonString);
        String[] columnNames;

        if(element.isJsonObject())
        {
            JsonObject serverData = element.getAsJsonObject();
            JsonElement allData = serverData.get("data");
            templateName = serverData.get("report_name").toString();

            JsonArray dataArray = allData.getAsJsonArray();

            columnNames = new String[dataArray.size()];

            for(int i = 0; i < dataArray.size(); i++)
            {
                JsonObject columnData = dataArray.get(i).getAsJsonObject();

                String columnName = columnData.get("column_name").getAsString();

                columnNames[i] = columnName;

                String dataInstances = columnData.get("Data").toString();

                String[] dataOccurrenceValues = arrayStringTokenizer(dataInstances);

                String dataValues = columnData.get("DataFields").toString();

                String[] dataValuesArray = arrayStringTokenizer(dataValues);

                String[][] columnDataArray = new String[dataValuesArray.length][2];
                for(int j = 0; j < dataValuesArray.length; j++)
                {
                    columnDataArray[j][0] = dataValuesArray[j];
                    columnDataArray[j][1] = dataOccurrenceValues[j];
                }

                reportData.put(columnName, columnDataArray);
            }
            return new ReportDataModel(columnNames, reportData, templateName);
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns a string array of a string that contains elements separated by a token.
     * @param arrayString array in string form.
     * @return a string array of all the elements in the string.
     */
    public static String[] arrayStringTokenizer(String arrayString)
    {
        StringBuilder sb = new StringBuilder(arrayString);
        sb.deleteCharAt(0);
        sb.deleteCharAt(sb.length() - 1);

        String removedBracketsString = sb.toString();

        String[] dataValues = removedBracketsString.split(",");

        return dataValues;
    }

    public static String jsonFromFiles (List<File> files, String templatename) throws IOException{
        return jsonFromClientList(clientListFromFileList(files), templatename);
    }




}