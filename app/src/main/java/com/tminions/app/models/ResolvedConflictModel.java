package com.tminions.app.models;

import java.util.Map;

public class ResolvedConflictModel {

    private String conflict_id;
    private String TEMPLATE_NAME;
    private String unique_identifier;
    private Map<String, String> resolution;

    public ResolvedConflictModel(String conflict_id, String TEMPLATE_NAME, String unique_identifier, Map<String, String> resolution) {
        this.conflict_id = conflict_id;
        this.TEMPLATE_NAME = TEMPLATE_NAME;
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

    public Map<String, String> getResolution() {
        return resolution;
    }

    public void setResolution(Map<String, String> resolution) {
        this.resolution = resolution;
    }
}
