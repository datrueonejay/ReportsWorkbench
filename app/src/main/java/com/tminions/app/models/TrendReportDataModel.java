package com.tminions.app.models;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TrendReportDataModel
{
    private String[] categories;
    private HashMap<String, Integer> trendReportData;
    private String templateName;

    private String closestColumn;


    public TrendReportDataModel(String[] categories, HashMap<String, Integer> trendReportData, String templateName, String closestColumn)
    {
        this.categories = categories;
        this.trendReportData = trendReportData;
        this.templateName = templateName;
        this.closestColumn = closestColumn;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public HashMap<String, Integer> getTrendReportData() {
        return trendReportData;
    }

    public void setTrendReportData(HashMap<String, Integer> trendReportData) {
        this.trendReportData = trendReportData;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getClosestColumn() {
        return closestColumn;
    }

    public void setClosestColumn(String closestColumn) {
        this.closestColumn = closestColumn;
    }

    @Override
    public String toString()
    {
        String allReportData = "The data in the trends report is " +
                               "[" + templateName + "]" + "\n";

        Set set = trendReportData.entrySet();
        Iterator it = set.iterator();
        while(it.hasNext())
        {
            Map.Entry mEntry = (Map.Entry)it.next();
            allReportData += "The value of the catagory is: " + mEntry.getKey() + "\n";
            int catagoryValue = trendReportData.get(mEntry.getKey());
            allReportData += "The value for this catagory is: " + String.valueOf(catagoryValue) + "\n";
        }

        return allReportData;
    }
}
