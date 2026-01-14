package org.wadr.ui.panels;

import org.wadr.service.TaskManager;
import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {
    private JLabel totalLabel;
    private JLabel pendingLabel;
    private JLabel completedLabel;
    
    public StatusPanel() {
        initComponents();
        setupLayout();
        updateStats();
    }
    
    private void initComponents() {
        totalLabel = new JLabel();
        pendingLabel = new JLabel();
        completedLabel = new JLabel();
        
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        pendingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        completedLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    }
    
    private void setupLayout() {
        setLayout(new GridLayout(1, 3, 10, 0));
        setBorder(BorderFactory.createTitledBorder("Estadísticas"));
        
        add(createStatPanel("Total", totalLabel, Color.BLUE));
        add(createStatPanel("Pendientes", pendingLabel, Color.ORANGE));
        add(createStatPanel("Completadas", completedLabel, Color.GREEN));
    }
    
    private JPanel createStatPanel(String title, JLabel valueLabel, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        titleLabel.setForeground(Color.GRAY);
        
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        valueLabel.setForeground(color);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);
        
        return panel;
    }
    
    public void updateStats() {
        TaskManager manager = TaskManager.getInstance();
        totalLabel.setText(String.valueOf(manager.getTotalTasks()));
        pendingLabel.setText(String.valueOf(manager.getPendingTasks().size()));
        completedLabel.setText(String.valueOf(manager.getCompletedCount()));
    }
}