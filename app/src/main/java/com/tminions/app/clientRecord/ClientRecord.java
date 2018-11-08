package com.tminions.app.clientRecord;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringJoiner;

public class ClientRecord {

    private String uniqueID;
    private HashMap<String, String> data;
    private ArrayList<String> invalidKeys;

    
	/**
     * for when u don't have the client's data yet
     */
    public ClientRecord() {
        this.data = new HashMap<>();
        this.data.put("valid", "true");
        this.invalidKeys = new ArrayList<>();
    }

    /**
     * for when u already have a hashmap of client's data
     * @param data
     */
    public ClientRecord(HashMap<String, String> data) {
        this.data = data;
        this.invalidKeys = new ArrayList<>();
        this.autoSetUniqueId();
    }

    public String getUniqueID() {
    	if (this.uniqueID == null || this.uniqueID.isEmpty()) {
    		this.autoSetUniqueId();
    	}
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

        if (this.uniqueID == null || this.uniqueID.isEmpty()){
            this.autoSetUniqueId();
        }

        StringJoiner sj = new StringJoiner(",\n\t", "\"" + this.uniqueID + "\" : {\n\t", "\n}");

        for (String key: this.data.keySet()) {
            String value = this.data.get(key);
            sj.add("\"" + key + "\" : \"" + value + "\"");
        }

        if (!this.invalidKeys.isEmpty()) {
            StringJoiner invalSj = new StringJoiner(", ", "[", "]");
            for (String key: this.invalidKeys) {
                invalSj.add(key);
            }
            sj.add("\"invalid_rows\" : " + invalSj.toString());
        }
        return sj.toString();

    }

    /**
     * add to this client record's data
     * @param key the key
     * @param value the value
     */
    public void put(String key, String value) {
        this.data.put(key, value);
    }

    /**
     * add poorly formatted data to this client's data.
     * this adds the key, value to their data hashmap but also flags that key
     * as having badly formatted data.
     * @param key
     * @param value
     */
    public void putInvalid(String key, String value) {
        this.data.put(key, value);
        this.invalidKeys.add(key);
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
            if (key.toLowerCase().contains("validation_id")) {
                this.uniqueID = this.data.get(key);
                return;
            }
        }
        for (String key: this.data.keySet()) {
            if (key.toLowerCase().contains("record_id")) {
                this.uniqueID = this.data.get(key);
                return;
            }
        }

        this.uniqueID = Integer.toString(this.hashCode());


    }

    public ArrayList<String> getInvalidKeys() {
		return invalidKeys;
	}

	public void setInvalidKeys(ArrayList<String> invalidKeys) {
		this.invalidKeys = invalidKeys;
	}

}
