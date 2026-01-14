package org.wadr.utils;

import org.wadr.model.Task;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class FormatterUtil {
    private static final DateTimeFormatter DATE_FORMATTER = 
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return "N/A";
        return dateTime.format(DATE_FORMATTER);
    }
    
    public static String formatTaskDetails(Task task) {
        if (task == null) return "Tarea no encontrada";
        
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(task.getId()).append("\n");
        sb.append("Título: ").append(task.getTitle()).append("\n");
        sb.append("Descripción: ").append(task.getDescription()).append("\n");
        sb.append("Estado: ").append(task.isCompleted() ? "Completada" : "Pendiente").append("\n");
        sb.append("Creada: ").append(formatDateTime(task.getCreatedAt()));
        
        return sb.toString();
    }
    
    public static String truncateText(String text, int maxLength) {
        if (text == null) return "";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }
}