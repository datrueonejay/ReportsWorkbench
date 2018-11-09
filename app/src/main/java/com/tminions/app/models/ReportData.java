package com.tminions.app.models;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ReportData
{

    private String[] columnNames;
    private Map reportData = new HashMap<String, String[]>();
    private String templateName;


    public ReportData()
    {
        this.columnNames = null;
        this.reportData = null;
        this.templateName = null;
    }


    public ReportData(String[] columnNames, Map reportData, String templateName)
    {
        this.columnNames = columnNames;
        this.reportData = reportData;
        this.templateName = templateName;
    }

    public String[] getColumnNames()
    {
        return this.columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public Map getReportData() {
        return reportData;
    }

    public void setReportData(Map reportData) {
        this.reportData = reportData;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    @Override
    public String toString()
    {
        String allReportData = "The data from the template " +
                               "[" + templateName + "]" + "\n";
        Iterator it  = reportData.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry pair = (Map.Entry)it.next();
            allReportData = "The column name is " + pair.getKey() + " = " + "The column data is" + pair.getValue() + ".";
            it.remove();
        }


        return allReportData;
    }
}