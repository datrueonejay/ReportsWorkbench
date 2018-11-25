package com.tminions.app.charts;

import com.tminions.app.jsonMaker.JsonMaker;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class GenerateBarGraphForTrends extends JFrame
{
    private final int DEFAULT_SIZE = 15;
    private final Color DEFAULT_COLOR = Color.white;
    private final String DEFAULT_ROW_KEY = "Occurrences in database";
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
        /*ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(DEFAULT_SIZE, DEFAULT_SIZE,
                DEFAULT_SIZE, DEFAULT_SIZE));
        chartPanel.setBackground(DEFAULT_COLOR);
        add(chartPanel);*/

        ChartUtils.createPNGfromChart(chart, fullFileName, DEFAULT_FILE_HEIGHT, DEFAULT_FILE_WIDTH);

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
