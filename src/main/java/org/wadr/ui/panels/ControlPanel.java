package org.wadr.ui.panels;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    private JButton addButton;
    private JButton completeButton;
    private JButton deleteButton;
    private JButton detailsButton;
    private JButton refreshButton;
    
    public ControlPanel() {
        initComponents();
        setupLayout();
    }
    
    private void initComponents() {
        addButton = new JButton("＋ Nueva Tarea");
        addButton.setToolTipText("Agregar nueva tarea");
        
        completeButton = new JButton("✓ Completar");
        completeButton.setToolTipText("Marcar tarea como completada");
        
        deleteButton = new JButton("✗ Eliminar");
        deleteButton.setToolTipText("Eliminar tarea seleccionada");
        
        detailsButton = new JButton("🔍 Detalles");
        detailsButton.setToolTipText("Ver detalles de la tarea");
        
        refreshButton = new JButton("↻ Actualizar");
        refreshButton.setToolTipText("Actualizar lista de tareas");
    }
    
    private void setupLayout() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setBorder(BorderFactory.createTitledBorder("Acciones"));
        
        add(addButton);
        add(completeButton);
        add(deleteButton);
        add(detailsButton);
        add(refreshButton);
    }
    
    // Getters para los botones
    public JButton getAddButton() { return addButton; }
    public JButton getCompleteButton() { return completeButton; }
    public JButton getDeleteButton() { return deleteButton; }
    public JButton getDetailsButton() { return detailsButton; }
    public JButton getRefreshButton() { return refreshButton; }
}