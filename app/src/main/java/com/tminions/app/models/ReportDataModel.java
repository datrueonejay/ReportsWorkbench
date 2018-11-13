package com.tminions.app.models;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ReportDataModel
{

    private String[] columnNames;
    private HashMap<String, String[][]> reportData;
    private String templateName;


    public ReportDataModel()
    {
        this.columnNames = null;
        this.reportData = null;
        this.templateName = null;
    }


    public ReportDataModel(String[] columnNames, HashMap<String, String[][]> reportData,
                           String templateName)
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

    public HashMap<String, String[][]> getReportData() {
        return reportData;
    }

    public void setReportData(HashMap<String, String[][]> reportData) {
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
        Set set = reportData.entrySet();
        Iterator it  = set.iterator();
        while(it.hasNext()){
            Map.Entry mEntry = (Map.Entry)it.next();
            allReportData += "The column name is: " + mEntry.getKey() + "\n";
            String[][] columnData = reportData.get(mEntry.getKey());
            for(int i = 0; i < columnData.length; i++)
            {
                allReportData += "Values" + "\n" + "Value: " + columnData[i][0] + "| Occurrences: "  + columnData[i][1]  + "\n";
            }
        }
        return allReportData;
    }
}