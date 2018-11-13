package com.tminions.app.charts;

import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;

import java.io.File;
import java.io.IOException;

public class ChartUtils
{
    public static void createPNGfromChart(JFreeChart chart, String fileName, int height, int width)
    {
        try {
            final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            final File file1 = new File(fileName);
            ChartUtilities.saveChartAsPNG(file1, chart, width, height, info);
        }
        catch (IOException ioException)
        {
            System.out.println("Error saving chart to file.");
        }
    }
}
