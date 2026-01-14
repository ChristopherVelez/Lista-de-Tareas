package org.wadr.ui;

import org.wadr.model.Task;
import org.wadr.service.TaskManager;
import org.wadr.ui.components.TaskListRenderer;
import org.wadr.ui.dialogs.TaskDetailDialog;
import org.wadr.ui.panels.ControlPanel;
import org.wadr.ui.panels.StatusPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MainFrame extends JFrame {
    private TaskManager taskManager;
    private DefaultListModel<Task> listModel;
    private JList<Task> taskList;
    private ControlPanel controlPanel;
    private StatusPanel statusPanel;
    
    public MainFrame() {
        taskManager = TaskManager.getInstance();
        initComponents();
        setupFrame();
        setupListeners();
        loadTasks();
    }
    
    private void initComponents() {
        // Configurar modelo y lista
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setCellRenderer(new TaskListRenderer());
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Crear paneles
        controlPanel = new ControlPanel();
        statusPanel = new StatusPanel();
        
        // Configurar layout principal
        setLayout(new BorderLayout(10, 10));
        
        // Panel de lista con scroll
        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Tareas"));
        
        // Agregar componentes al frame
        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
    }
    
    private void setupFrame() {
        setTitle("Gestor de Tareas - Proyecto Base Java");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Crear menú
        setupMenuBar();
    }
    
    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // Menú Archivo
        JMenu fileMenu = new JMenu("Archivo");
        fileMenu.setMnemonic(KeyEvent.VK_A);
        
        JMenuItem newItem = new JMenuItem("Nueva Tarea");
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        
        JMenuItem clearAllItem = new JMenuItem("Limpiar todas las tareas");
        clearAllItem.addActionListener(e -> clearAllTasks());
        
        JMenuItem exitItem = new JMenuItem("Salir");
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        
        fileMenu.add(newItem);
        fileMenu.add(clearAllItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        // Menú Tareas
        JMenu taskMenu = new JMenu("Tareas");
        taskMenu.setMnemonic(KeyEvent.VK_T);
        
        JMenuItem completeItem = new JMenuItem("Marcar como completada");
        completeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        
        JMenuItem deleteItem = new JMenuItem("Eliminar tarea");
        deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        
        taskMenu.add(completeItem);
        taskMenu.add(deleteItem);
        
        // Menú Ayuda
        JMenu helpMenu = new JMenu("Ayuda");
        helpMenu.setMnemonic(KeyEvent.VK_H);
        
        JMenuItem aboutItem = new JMenuItem("Acerca de");
        aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(taskMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
        
        // Listeners del menú
        newItem.addActionListener(e -> showAddTaskDialog());
        exitItem.addActionListener(e -> System.exit(0));
        completeItem.addActionListener(e -> completeSelectedTask());
        deleteItem.addActionListener(e -> deleteSelectedTask());
        aboutItem.addActionListener(e -> showAboutDialog());
    }
    
    private void setupListeners() {
        // Botón Nueva Tarea
        controlPanel.getAddButton().addActionListener(e -> showAddTaskDialog());
        
        // Botón Marcar Completada
        controlPanel.getCompleteButton().addActionListener(e -> completeSelectedTask());
        
        // Botón Eliminar
        controlPanel.getDeleteButton().addActionListener(e -> deleteSelectedTask());
        
        // Botón Ver Detalles
        controlPanel.getDetailsButton().addActionListener(e -> showTaskDetails());
        
        // Botón Actualizar
        controlPanel.getRefreshButton().addActionListener(e -> refreshTaskList());
        
        // Doble clic en la lista
        taskList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    showTaskDetails();
                }
            }
        });
    }
    
    private void loadTasks() {
        listModel.clear();
        for (Task task : taskManager.getAllTasks()) {
            listModel.addElement(task);
        }
        statusPanel.updateStats();
    }
    
    
    private void clearAllTasks() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de que desea eliminar todas las tareas?",
            "Confirmar Eliminación Total",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            taskManager.clearAllTasks();
            loadTasks();
            JOptionPane.showMessageDialog(this, "Todas las tareas han sido eliminadas", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void showAddTaskDialog() {
        JTextField titleField = new JTextField(20);
        JTextArea descArea = new JTextArea(5, 20);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.add(new JLabel("Título:"));
        panel.add(titleField);
        panel.add(new JLabel("Descripción:"));
        panel.add(new JScrollPane(descArea));
        
        // Añadir padding al panel
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        container.add(panel, BorderLayout.CENTER);
        
        int result = JOptionPane.showConfirmDialog(
            this,
            container,
            "Nueva Tarea",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (result == JOptionPane.OK_OPTION) {
            String title = titleField.getText().trim();
            String description = descArea.getText().trim();
            
            if (!title.isEmpty()) {
                Task task = new Task(title, description);
                taskManager.addTask(task);
                loadTasks();
                JOptionPane.showMessageDialog(this, "Tarea agregada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "El título es requerido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void completeSelectedTask() {
        Task selected = taskList.getSelectedValue();
        if (selected != null) {
            if (!selected.isCompleted()) {
                taskManager.markTaskCompleted(selected.getId());
                loadTasks();
                JOptionPane.showMessageDialog(this, "Tarea marcada como completada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "La tarea ya está completada", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una tarea primero", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void deleteSelectedTask() {
        Task selected = taskList.getSelectedValue();
        if (selected != null) {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Eliminar la tarea '" + selected.getTitle() + "'?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (taskManager.removeTask(selected.getId())) {
                    loadTasks();
                    JOptionPane.showMessageDialog(this, "Tarea eliminada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar la tarea", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una tarea primero", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void showTaskDetails() {
        Task selected = taskList.getSelectedValue();
        if (selected != null) {
            TaskDetailDialog dialog = new TaskDetailDialog(this, selected);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una tarea primero", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void refreshTaskList() {
        loadTasks();
        JOptionPane.showMessageDialog(this, "Lista actualizada", "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showAboutDialog() {
        String aboutText = "<html>" +
            "<h2>Gestor de Tareas v1.1</h2>" +
            "<p>Proyecto Java para gestión de tareas - Versión Corregida</p>" +
            "<p><b>Correcciones:</b></p>" +
            "<ul>" +
            "<li>IDs únicos usando UUID</li>" +
            "<li>Eliminación individual de tareas corregida</li>" +
            "<li>Manejo mejorado de tareas de muestra</li>" +
            "</ul>" +
            "<hr>" +
            "<p><small>© 2024 - Proyecto Base para Ingeniería de Software</small></p>" +
            "</html>";
        
        JOptionPane.showMessageDialog(
            this,
            aboutText,
            "Acerca de",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    public void showFrame() {
        SwingUtilities.invokeLater(() -> {
            try {
                setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                    null,
                    "Error al mostrar la ventana: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}