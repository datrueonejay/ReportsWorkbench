package com.tminions.app.models;

import java.util.HashMap;
import java.util.List;

public class TemplateColumnsModel {

    private HashMap<String, String> _columns;
    private List<String> _mandatory_columns;
    private HashMap<String, String> _column_name_map;

    public HashMap<String, String> get_columns() {
        return _columns;
    }

    public void set_columns(HashMap<String, String> columns) {
        this._columns = columns;
    }

    public List<String> get_mandatory_columns() {
        return _mandatory_columns;
    }

    public void set_mandatory_columns(List<String> mandatoryColumns) {
        this._mandatory_columns = mandatoryColumns;
    }

    public HashMap<String, String> get_column_name_map() {
        return _column_name_map;
    }

    public void set_column_name_map(HashMap<String, String> _column_name_map) {
        this._column_name_map = _column_name_map;
    }
}
