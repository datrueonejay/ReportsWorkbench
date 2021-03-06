package com.tminions.app.charts;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class GenerateBarChartReport extends JFrame
{
    private final int DEFAULT_SIZE = 15;
    private final Color DEFAULT_COLOR = Color.white;
    private final String DEFAULT_ROW_KEY = "Occurrences in database";
    private String filePath;
    private final int DEFAULT_FILE_WIDTH = 600;
    private final int DEFAULT_FILE_HEIGHT = 600;

    public GenerateBarChartReport(String[][] columnData, String graphTitle,
                                  String valueAxisLabel, String categoryAxisLabel,
                                  String filePath)
    {
        this.filePath = filePath;
        initUI(columnData, graphTitle, valueAxisLabel, categoryAxisLabel);
    }


    private void initUI(String[][] columnData, String graphTitle,
                        String valueAxisLabel, String categoryAxisLabel)
    {
        CategoryDataset dataSet = generateDataset(columnData);

        JFreeChart chart = createChart(dataSet, graphTitle,
                                       valueAxisLabel, categoryAxisLabel);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(DEFAULT_SIZE, DEFAULT_SIZE,
                                                                DEFAULT_SIZE, DEFAULT_SIZE));
        chartPanel.setBackground(DEFAULT_COLOR);
        add(chartPanel);

        ChartUtils.createPNGfromChart(chart, filePath, DEFAULT_FILE_HEIGHT, DEFAULT_FILE_WIDTH);

        pack();
        setTitle(graphTitle);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private CategoryDataset generateDataset(String[][] columnData) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for(int i = 0; i < columnData.length; i++)
        {
            dataset.setValue(Integer.parseInt(columnData[i][1]), DEFAULT_ROW_KEY, columnData[i][0]);
        }

        //dataset.setValue(46, "Gold medals", "USA");
        //dataset.setValue(38, "Gold medals", "China");
        //dataset.setValue(29, "Gold medals", "UK");
        //dataset.setValue(22, "Gold medals", "Russia");
        //dataset.setValue(13, "Gold medals", "South Korea");
        //dataset.setValue(11, "Gold medals", "Germany");

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
