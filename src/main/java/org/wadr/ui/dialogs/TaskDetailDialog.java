package org.wadr.ui.dialogs;

import org.wadr.model.Task;
import org.wadr.utils.FormatterUtil;
import javax.swing.*;
import java.awt.*;

public class TaskDetailDialog extends JDialog {
    
    public TaskDetailDialog(Frame parent, Task task) {
        super(parent, "Detalles de Tarea", true);
        initComponents(task);
    }
    
    private void initComponents(Task task) {
        setLayout(new BorderLayout(10, 10));
        setSize(400, 300);
        setLocationRelativeTo(getParent());
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // TextArea para mostrar detalles
        JTextArea detailsArea = new JTextArea(FormatterUtil.formatTaskDetails(task));
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(detailsArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Información de la Tarea"));
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
}