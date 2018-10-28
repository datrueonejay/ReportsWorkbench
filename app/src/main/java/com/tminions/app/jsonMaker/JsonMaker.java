package com.tminions.app.jsonMaker;

import com.tminions.app.Normalizer.DateVerifier;
import com.tminions.app.Normalizer.Normalizer;
import com.tminions.app.Normalizer.PhoneVerifier;
import com.tminions.app.Normalizer.PostalCodeVerifier;
import com.tminions.app.clientRecord.ClientRecord;
import com.tminions.app.fileParsers.ExcelParser;
import org.apache.commons.lang3.RegExUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

    public static String jsonFromFiles (List<File> files, String templatename) throws IOException{
        return jsonFromClientList(clientListFromFileList(files), templatename);
    }

}
