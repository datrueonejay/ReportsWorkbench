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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;

public class JsonMaker {

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

    /**
     * Returns a RDM object so that the data can then be plotted in the graphs.
     * @param jsonString
     * @return ReportDataModel Object containing data from the server.
     */
    public static ReportDataModel convertJsonResponseToRDM(String jsonString)
    {
        String templateName;
        HashMap<String, String[][]> reportData = new HashMap<String, String[][]>();
        //System.out.println("The value of the jsonString from the server is :" + jsonString);
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonString);
        String[] columnNames;

        if(element.isJsonObject())
        {
            JsonObject serverData = element.getAsJsonObject();
            JsonElement allData = serverData.get("data");
            templateName = serverData.get("report_name").toString();

            //System.out.println("The template name is " + templateName);

            //System.out.println(serverData.get("data"));
            JsonArray dataArray = allData.getAsJsonArray();

            System.out.println(dataArray.get(0).getAsJsonObject().toString());
            columnNames = new String[dataArray.size()];


            for(int i = 0; i < dataArray.size(); i++)
            {
                JsonObject columnData = dataArray.get(i).getAsJsonObject();

                String columnName = columnData.get("column_name").getAsString();

                columnNames[i] = columnName;

                //System.out.println("The columnName is: " + columnName);
                //System.out.println("The value of data instances is: " + columnData.get("Data"));

                String dataInstances = columnData.get("Data").toString();
                //System.out.println("The value of the data instances as a string is:" + dataInstances);

                String[] dataOccurrenceValues = arrayStringTokenizer(dataInstances);

                String dataValues = columnData.get("DataFields").toString();
                //System.out.println("The value of the data values as a string are:" + dataValues);

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
            System.out.println("The element in the json string from the server is not a json object.");
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

        //System.out.println("The value of the data after removed bracket strings is: " + removedBracketsString);

        String[] dataValues = removedBracketsString.split(",");

        //for(int j = 0; j < dataValues.length; j++)
        //{
            //System.out.println("Value : " + dataValues[j]);
        //}

        return dataValues;
    }

    public static String jsonFromFiles (List<File> files, String templatename) throws IOException{
        return jsonFromClientList(clientListFromFileList(files), templatename);
    }

}
