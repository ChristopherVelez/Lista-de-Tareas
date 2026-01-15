package org.wadr;

import org.junit.Before;
import org.junit.Test;
import org.wadr.model.Task;

import static org.junit.Assert.*;
import org.wadr.service.TaskManager;

/**
 * Unit test for simple App.
 */
public class AppTest {
    // Instancia del gestor de tareas (patrón Singleton)

    private TaskManager taskManager;
    // Método que se ejecuta antes de cada prueba
    // Se utiliza para inicializar el gestor y limpiar el estado previo
    @Before
    public void setUp() {
        taskManager = TaskManager.getInstance();
        taskManager.clearAllTasks(); // limpiar estado previo
    }
        // Prueba unitaria para verificar que una tarea se agrega correctamente

    @Test
    public void testAddTask() {
        Task task = new Task("Tarea JUnit4", "Descripción");
        // Se agrega la tarea al gestor
        taskManager.addTask(task);
        // Se valida que el total de tareas sea 1
        assertEquals(1, taskManager.getTotalTasks());
    }
    // Prueba unitaria para verificar que una tarea puede marcarse como completada
    @Test
    public void testMarkTaskCompleted() {
        // Se crea y agrega una nueva tarea
        Task task = new Task("Completar tarea", "Descripción");
        taskManager.addTask(task);
        // Se marca la tarea como completada usando su ID
        taskManager.markTaskCompleted(task.getId());
        // Se recupera la tarea y se valida su estado
        Task result = taskManager.getTaskById(task.getId());
        assertNotNull(result);// La tarea debe existir
        assertTrue(result.isCompleted());// La tarea debe estar completada
    }
    // Prueba unitaria para verificar la eliminación de una tarea

    @Test
    public void testRemoveTask() {
                // Se crea y agrega una tarea

        Task task = new Task("Eliminar tarea", "Descripción");
        taskManager.addTask(task);
        // Se elimina la tarea utilizando su ID
        boolean removed = taskManager.removeTask(task.getId());
                // Se valida que la tarea fue eliminada correctamente
        assertTrue(removed);
        assertEquals(0, taskManager.getTotalTasks());
    }
}