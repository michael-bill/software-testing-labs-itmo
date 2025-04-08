package ru.michael.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import ru.michael.math.logarifm.LnFunction;
import ru.michael.math.logarifm.Log10Function;
import ru.michael.math.logarifm.Log2Function;
import ru.michael.math.logarifm.Log3Function;
import ru.michael.math.logarifm.Log5Function;
import ru.michael.math.trigonometry.CosFunction;
import ru.michael.math.trigonometry.CotFunction;
import ru.michael.math.trigonometry.SecFunction;
import ru.michael.math.trigonometry.SinFunction;

public class FunctionViewer {

    private static String path = null; // Путь для сохранения графиков

    public static void main(String[] args) {
        path = "/Users/michael-bill/Desktop/functions";

        SwingUtilities.invokeLater(() -> {
            createAndShowChart("f(x) - основная система функций", ru.michael.app.Function::f, path);

            createAndShowChart("sec(x) - секанс", SecFunction::sec, path);
            createAndShowChart("cot(x) - котангенс", CotFunction::cot, path);
            createAndShowChart("cos(x) - косинус", CosFunction::cos, path);

            createAndShowChart("log_10(x) - логарифм по основанию 10", Log10Function::log_10, path);
            createAndShowChart("log_5(x) - логарифм по основанию 5", Log5Function::log_5, path);
            createAndShowChart("log_3(x) - логарифм по основанию 3", Log3Function::log_3, path);
            createAndShowChart("log_2(x) - логарифм по основанию 2", Log2Function::log_2, path);

            createAndShowChart("sin(x) - синус", SinFunction::sin, path);
            createAndShowChart("ln(x) - натуральный логарифм", LnFunction::ln, path);
        });
    }

    private static void createAndShowChart(String title, Function function, String imgDirPath) {
        XYSeries series = new XYSeries(title);

        double yLimit = 20;
        double epsilon = 1e-8;
        double start = -5.0;
        double end = 5.0;
        double step = 0.0011;

        for (double x = start; x <= end; x += step) {
            try {
                double y = function.calculate(x, epsilon);
                if (y > yLimit) {
                    y = yLimit;
                }
                if (y < -yLimit) {
                    y = -yLimit;
                }
                series.add(x, y);
            } catch (Exception ignored) { }
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        chart.setBackgroundPaint(Color.white);

        if (imgDirPath != null) {
            try {
                String fileName = title + ".png";
                File outputFile = new File(imgDirPath, fileName);

                File parentDir = outputFile.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                    if (!parentDir.mkdirs()) {
                        throw new RuntimeException(
                                "Не удалось создать директорию: " + parentDir.getAbsolutePath());
                    }
                }

                ChartUtils.saveChartAsPNG(outputFile, chart, 800, 600);
            } catch (IOException e) {
                System.err.println("Ошибка сохранения: " + e.getMessage());
            }
        }

        ChartFrame frame = new ChartFrame(title, chart);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @FunctionalInterface
    private interface Function {
        double calculate(double x, double epsilon);
    }
}
