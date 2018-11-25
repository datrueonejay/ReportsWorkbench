package com.tminions.app.charts;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JFrame;

import org.jfree.chart.*;
import org.jfree.data.general.DefaultPieDataset;

public class GeneratePieChartReport extends JFrame {

    private final int DEFAULT_SIZE = 15;
    private final Color DEFAULT_COLOR = Color.white;
    private String filePath;
    private final int DEFAULT_FILE_WIDTH = 600;
    private final int DEFAULT_FILE_HEIGHT = 600;

    public GeneratePieChartReport(String[][] columnData, String graphTitle, String filePath) {

        this.filePath = filePath;
        initUI(columnData, graphTitle);
    }

    private void initUI(String[][] columnData, String graphTitle) {

        DefaultPieDataset dataset = createDataset(columnData);

        JFreeChart chart = createChart(dataset, graphTitle);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(DEFAULT_SIZE,
                DEFAULT_SIZE, DEFAULT_SIZE, DEFAULT_SIZE));
        chartPanel.setBackground(DEFAULT_COLOR);
        //add(chartPanel);

        ChartUtils.createPNGfromChart(chart, filePath, DEFAULT_FILE_HEIGHT, DEFAULT_FILE_WIDTH);

        pack();
        setTitle(graphTitle);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private DefaultPieDataset createDataset(String[][] columnData)
    {
        DefaultPieDataset dataset = new DefaultPieDataset();

        int totalOccurrences = 0;
        for(int i = 0; i < columnData.length; i++)
        {
           totalOccurrences += Integer.parseInt(columnData[i][1]);
        }

        for(int i = 0; i < columnData.length; i++)
        {
            double chartValue = ((Integer.parseInt(columnData[i][1])) * 100) / totalOccurrences;
            dataset.setValue(columnData[i][0], chartValue);
        }
        return dataset;
    }

    private JFreeChart createChart(DefaultPieDataset dataset, String title) {

        JFreeChart barChart = ChartFactory.createPieChart(
                title,
                dataset,
                false, true, false);

        return barChart;
    }

}