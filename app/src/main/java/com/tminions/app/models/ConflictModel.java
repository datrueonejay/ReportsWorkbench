package com.tminions.app.models;

import java.util.List;
import java.util.Map;

public class ConflictModel {

    private String _id;
    private String template_name;
    private String unique_identifier;
    private List<Map<String, String>> conflicts;

    public ConflictModel(String _id, String template_name, String unique_identifier, List<Map<String, String>> conflicts) {
        this._id = _id;
        this.template_name = template_name;
        this.unique_identifier = unique_identifier;
        this.conflicts = conflicts;
    }

    public ConflictModel() {

    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTemplate_name() {
        return template_name;
    }

    public void setTemplate_name(String template_name) {
        this.template_name = template_name;
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
