package com.tminions.app.charts;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.IIOException;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.*;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.data.general.DefaultPieDataset;

public class GeneratePieChartReport extends JFrame {

    private final int DEFAULT_SIZE = 15;
    private final Color DEFAULT_COLOR = Color.white;
    private final String DEFAULT_FILE_NAME = "PieChart.png";
    private final int DEFAULT_FILE_WIDTH = 600;
    private final int DEFAULT_FILE_HEIGHT = 600;

    public GeneratePieChartReport(String[][] columnData, String graphTitle) {

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

        ChartUtils.createPNGfromChart(chart, DEFAULT_FILE_NAME, DEFAULT_FILE_HEIGHT, DEFAULT_FILE_WIDTH);

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

        //System.out.println("The value of total occurrences is: " + String.valueOf(totalOccurrences));

        for(int i = 0; i < columnData.length; i++)
        {
            double chartValue = ((Integer.parseInt(columnData[i][1])) * 100) / totalOccurrences;
            //System.out.println((chartValue * 100) / totalOccurrences);
            dataset.setValue(columnData[i][0], chartValue);
            //int chartValue = (Integer.parseInt(columnData[i][1]) / totalOccurrences) * 100;
            //System.out.println("The percentage value of the value : " + columnData[i][0] + " is :" + String.valueOf(chartValue));
            //dataset.setValue(columnData[i][0], chartValue);
        }
        //dataset.setValue("Apache", 52);
        //dataset.setValue("Nginx", 31);
        //dataset.setValue("IIS", 12);
        //dataset.setValue("LiteSpeed", 2);
        //dataset.setValue("Google server", 1);
        //dataset.setValue("Others", 2);

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