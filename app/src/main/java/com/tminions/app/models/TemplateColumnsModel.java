package com.tminions.app.models;

import java.util.HashMap;
import java.util.List;

public class TemplateColumnsModel {

    private HashMap<String, String> columns;
    private List<String> mandatoryColumns;

    public HashMap<String, String> getColumns() {
        return columns;
    }

    public void setColumns(HashMap<String, String> columns) {
        this.columns = columns;
    }

    public List<String> getMandatoryColumns() {
        return mandatoryColumns;
    }

    public void setMandatoryColumns(List<String> mandatoryColumns) {
        this.mandatoryColumns = mandatoryColumns;
    }
}
