package com.tminions.app;


import com.tminions.app.controllers.GenerateReportsController;
import com.tminions.app.models.LoginModel;
import com.tminions.app.models.ReportDataModel;

public class ReportGenerationScreenController {

    private static String defaulTemplate = "Employment Services";


    public void selectReport()
    {
        System.out.println("Selected Language report.");
        String[] columns = {"Language of service"};
        ReportDataModel rdm = GenerateReportsController.getReportData(columns, defaulTemplate);

    }
    
    public void selectReport2() {
    	System.out.println("Selected type of Institution/Organization Where Client Received Services.");
    	String[] columns = {"Type of Institution/Organization Where Client Received Services"};
    	ReportDataModel rdm = GenerateReportsController.getReportData(columns, defaulTemplate);
    }

}
