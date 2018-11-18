package com.tminions.app.models;

import java.util.List;
import java.util.Map;

public class ConflictModel {

    private String _id;
    private String TEMPLATE_NAME;
    private String unique_identifier;
    private List<Map<String, String>> conflicts;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTEMPLATE_NAME() {
        return TEMPLATE_NAME;
    }

    public void setTEMPLATE_NAME(String TEMPLATE_NAME) {
        this.TEMPLATE_NAME = TEMPLATE_NAME;
    }

    public String getUnique_identifier() {
        return unique_identifier;
    }

    public void setUnique_identifier(String unique_identifier) {
        this.unique_identifier = unique_identifier;
    }

    public List<Map<String, String>> getConflicts() {
        return conflicts;
    }

    public void setConflicts(List<Map<String, String>> conflicts) {
        this.conflicts = conflicts;
    }
}
