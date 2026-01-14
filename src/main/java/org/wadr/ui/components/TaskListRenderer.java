package org.wadr.ui.components;

import org.wadr.model.Task;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TaskListRenderer extends JPanel implements ListCellRenderer<Task> {
    private JLabel titleLabel;
    private JLabel statusLabel;
    private JLabel dateLabel;
    
    public TaskListRenderer() {
        setLayout(new BorderLayout(10, 5));
        setBorder(new EmptyBorder(8, 10, 8, 10));
        
        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        statusLabel = new JLabel();
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        
        dateLabel = new JLabel();
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        dateLabel.setForeground(Color.GRAY);
        
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.add(titleLabel, BorderLayout.NORTH);
        infoPanel.add(statusLabel, BorderLayout.CENTER);
        
        add(infoPanel, BorderLayout.CENTER);
        add(dateLabel, BorderLayout.EAST);
    }
    
    @Override
    public Component getListCellRendererComponent(JList<? extends Task> list, Task task, 
                                                   int index, boolean isSelected, boolean cellHasFocus) {
        
        titleLabel.setText(task.getTitle());
        
        if (task.isCompleted()) {
            statusLabel.setText("✓ Completada");
            statusLabel.setForeground(new Color(0, 128, 0));
            titleLabel.setForeground(Color.GRAY);
        } else {
            statusLabel.setText("⏳ Pendiente");
            statusLabel.setForeground(new Color(200, 100, 0));
            titleLabel.setForeground(Color.BLACK);
        }
        
        dateLabel.setText(org.wadr.utils.FormatterUtil.formatDateTime(task.getCreatedAt()));
        
        if (isSelected) {
            setBackground(new Color(220, 235, 255));
            setOpaque(true);
        } else {
            setBackground(index % 2 == 0 ? Color.WHITE : new Color(248, 248, 248));
            setOpaque(true);
        }
        
        if (cellHasFocus) {
            setBorder(BorderFactory.createLineBorder(new Color(100, 150, 255), 1));
        } else {
            setBorder(new EmptyBorder(8, 10, 8, 10));
        }
        
        return this;
    }
}