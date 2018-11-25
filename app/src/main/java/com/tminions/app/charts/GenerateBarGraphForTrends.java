package com.tminions.app.charts;

import com.tminions.app.jsonMaker.JsonMaker;
import com.tminions.app.pdfMaker.PdfMaker;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

public class GenerateBarGraphForTrends extends JFrame
{
    private final int DEFAULT_FILE_WIDTH = 600;
    private final int DEFAULT_FILE_HEIGHT = 600;

    public GenerateBarGraphForTrends(HashMap<String, Integer> graphData, String graphTitle,
                                     String valueAxisLabel, String categoryAxisLabel, String fileName)
    {
        initUI(graphData, graphTitle, valueAxisLabel, categoryAxisLabel, fileName);
    }


    private void initUI(HashMap<String, Integer> graphData, String graphTitle,
                        String valueAxisLabel, String categoryAxisLabel, String fileName)
    {

        String fullFileName = "Trend Report For " + fileName + ".png";

        CategoryDataset dataSet = generateDataset(graphData, categoryAxisLabel);

        JFreeChart chart = createChart(dataSet, graphTitle,
                valueAxisLabel, categoryAxisLabel);

        ChartUtils.createPNGfromChart(chart, fullFileName, DEFAULT_FILE_HEIGHT, DEFAULT_FILE_WIDTH);

        String pdfFileName = "Trend Report For " + fileName + ".pdf";
        try {
            PdfMaker pdf = new PdfMaker(pdfFileName);
            pdf.addTitle("Trend Report For " + fileName);
            pdf.addImage(fullFileName, 50, 50, 500, 500);
            pdf.saveAndClose();

        } catch (IOException e) {
            e.printStackTrace();
        }

        pack();
        setTitle(graphTitle);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private CategoryDataset generateDataset(HashMap<String, Integer> graphData, String categoryAxisLabel) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();


        System.out.println("The value of the valueAxisLabel is: " + categoryAxisLabel);


        String[] data = JsonMaker.MONTHS;
        if(categoryAxisLabel.equals("By Age"))
        {
            data = JsonMaker.AGE_GROUPS;
        }
        else if(categoryAxisLabel.equals("By Month"))
        {
            data = JsonMaker.MONTHS;
        }

        for(int i = 0; i < data.length; i++)
        {
            System.out.println("The data was added to the dataset.");
            dataset.setValue(graphData.get(data[i]),"Users of service", data[i]);
        }

        return dataset;
    }

    private JFreeChart createChart(CategoryDataset dataSet, String graphTitle,
                                   String valueAxisLabel, String categoryAxisLabel)
    {
        JFreeChart barChart = ChartFactory.createBarChart(
                graphTitle,
                categoryAxisLabel,
                valueAxisLabel,
                dataSet,
                PlotOrientation.VERTICAL,
                false, true, false);

        return barChart;
    }
}
