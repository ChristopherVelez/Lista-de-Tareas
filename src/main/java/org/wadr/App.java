package org.wadr;

import org.wadr.ui.MainFrame;
import javax.swing.*;

public class App {
    public static void main(String[] args) {
        // Configurar look and feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Advertencia: No se pudo cargar el look and feel del sistema. Usando default.");
        }
        
        // Configurar algunos aspectos de la UI
        UIManager.put("OptionPane.messageFont", new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        UIManager.put("OptionPane.buttonFont", new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        
        // Iniciar aplicación en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                MainFrame mainFrame = new MainFrame();
                mainFrame.showFrame();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(
                    null,
                    "<html><b>Error crítico al iniciar la aplicación:</b><br>" + 
                    e.getClass().getSimpleName() + ": " + e.getMessage() + 
                    "</html>",
                    "Error Crítico",
                    JOptionPane.ERROR_MESSAGE
                );
                System.exit(1);
            }
        });
    }
}