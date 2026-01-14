package org.wadr.service;

import org.wadr.model.Task;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TaskManager {
    private List<Task> tasks;
    private static TaskManager instance;
    
    private TaskManager() {
        tasks = new ArrayList<>();
        initializeSampleTasks();
    }
    
    public static synchronized TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }
    
    private void initializeSampleTasks() {
        // Agregar una pequeña pausa entre la creación de tareas de muestra
        // para evitar problemas con timestamps duplicados
        addTask(new Task("Configurar proyecto", "Configurar estructura del proyecto Java"));
        
        try {
            Thread.sleep(10); // Pequeña pausa
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        addTask(new Task("Diseñar interfaz", "Crear interfaces gráficas con JFrame"));
        
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        addTask(new Task("Implementar lógica", "Desarrollar funcionalidad principal"));
        
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        addTask(new Task("Probar aplicación", "Realizar pruebas de funcionalidad"));
    }
    
    public void addTask(Task task) {
        tasks.add(task);
    }
    
    public boolean removeTask(String taskId) {
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getId().equals(taskId)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
    
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }
    
    public List<Task> getPendingTasks() {
        List<Task> pending = new ArrayList<>();
        for (Task task : tasks) {
            if (!task.isCompleted()) {
                pending.add(task);
            }
        }
        return pending;
    }
    
    public List<Task> getCompletedTasks() {
        List<Task> completed = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isCompleted()) {
                completed.add(task);
            }
        }
        return completed;
    }
    
    public Task getTaskById(String id) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }
    
    public void markTaskCompleted(String taskId) {
        Task task = getTaskById(taskId);
        if (task != null) {
            task.setCompleted(true);
        }
    }
    
    public int getTotalTasks() {
        return tasks.size();
    }
    
    public int getCompletedCount() {
        int count = 0;
        for (Task task : tasks) {
            if (task.isCompleted()) {
                count++;
            }
        }
        return count;
    }
    
    // Método para limpiar todas las tareas
    public void clearAllTasks() {
        tasks.clear();
    }
}