package com.tminions.app.clientRecord;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.StringJoiner;

public class ClientRecord {

    private String uniqueID;
    private HashMap<String, String> data = new HashMap<String, String>();

    public ClientRecord(HashMap<String, String> data) {
        this.data = data;
        this.autoSetUniqueId();
    }

    public String getUniqueID() {
        return this.uniqueID;
    }

    public HashMap<String, String> getData() {
        return this.data;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }

    /**
     * returns:
     * "[uniqueId]" : {
     *     "[hashmap.firstKey]" : "[hashmap.firstKeysValue]",
     *     [etc]
     *
     * }
     */
    public String toJson() {

        StringJoiner sj = new StringJoiner(",\n\t", "\"" + this.uniqueID + "\" : {\n\t", "\n}");

        for (String key: this.data.keySet()) {
            String value = this.data.get(key);
            sj.add("\"" + key + "\" : \"" + value + "\"");
        }
        return sj.toString();

    }

    /**
     * tries to find a key with "value" in the name
     * and if it does then it sets this.uniqueId to that key's value
     * if not then finds a key with "record id" in the name and
     * if it does then it sets this.uniqueId to that key's value.
     * If neither exist then sets it to this.hashCode();
     */
    public void autoSetUniqueId(){
        for (String key: this.data.keySet()) {
            if (key.toLowerCase().contains("value")) {
                this.uniqueID = this.data.get(key);
                return;
            }
        }
        for (String key: this.data.keySet()) {
            if (key.toLowerCase().contains("record id")) {
                this.uniqueID = this.data.get(key);
                return;
            }
        }

        this.uniqueID = Integer.toString(this.hashCode());


    }
}
