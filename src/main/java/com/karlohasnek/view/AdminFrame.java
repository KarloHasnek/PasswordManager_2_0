package com.karlohasnek.view;

import com.karlohasnek.controllers.PasswordEntryDAO;
import com.karlohasnek.controllers.UserDAO;
import com.karlohasnek.models.User;
import net.miginfocom.swing.MigLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class AdminFrame extends JFrame {

    private PasswordEntryDAO passwordEntryDAO;
    private ChartPanel chartPanel;

    public AdminFrame() {
        super("Admin Panel");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/resources/icon16.png");
        setIconImage(icon);

        initComps();
        layoutComps();
    }

    private void layoutComps() {
        setLayout(new MigLayout("", "[grow]", "[grow]"));
        add(chartPanel, "w90%, h90%");
    }

    private void initComps() {
        passwordEntryDAO = new PasswordEntryDAO();
        chartPanel = new ChartPanel(createChart(createDataset()));
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Map<String, Integer> passwordChangeCounts = passwordEntryDAO.getPasswordChangeCounts();

        for (Map.Entry<String, Integer> entry : passwordChangeCounts.entrySet()) {
            dataset.addValue(entry.getValue(), "Password Changes", entry.getKey());
        }

        return dataset;
    }

    private JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "User Password Change Counts", // Chart title
                "User",                        // Category axis label
                "Times Changed",               // Value axis label
                dataset,                       // Dataset
                PlotOrientation.VERTICAL,      // Bar chart orientation
                true,                          // Include legend
                true,                          // Tooltips
                false                          // URLs
        );

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        return chart;
    }
}
