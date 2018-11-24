package com.tminions.app.models;

import java.util.Map;

public class ResolvedConflictModel {

    private String conflict_id;
    private String template_name;
    private String unique_identifier;
    private Map<String, String> resolution;

    public ResolvedConflictModel(String conflict_id, String template_name, String unique_identifier, Map<String, String> resolution) {
        this.conflict_id = conflict_id;
        this.template_name = template_name;
        this.unique_identifier = unique_identifier;
        this.resolution = resolution;
    }

    public ResolvedConflictModel() {

    }

    public String getConflict_id() {
        return conflict_id;
    }

    public void setConflict_id(String conflict_id) {
        this.conflict_id = conflict_id;
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

    public Map<String, String> getResolution() {
        return resolution;
    }

    public void setResolution(Map<String, String> resolution) {
        this.resolution = resolution;
    }
}
