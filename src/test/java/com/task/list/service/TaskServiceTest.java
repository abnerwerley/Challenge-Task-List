package com.task.list.service;

import com.task.list.entity.EnumPriority;
import com.task.list.entity.EnumStatus;
import com.task.list.entity.Task;
import com.task.list.entity.mapper.TaskMapper;
import com.task.list.exception.RequestException;
import com.task.list.exception.ResourceNotFoundException;
import com.task.list.json.TaskForm;
import com.task.list.json.TaskResponse;
import com.task.list.repository.TaskRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @InjectMocks
    private TaskService service;

    public static final Long ID = 1L;
    public static final String DESCRIPTION = "Cool task to do.";
    public static final EnumStatus STATUS = EnumStatus.PENDING;

    public static final EnumPriority PRIORITY = EnumPriority.LOW;

    public static final EnumStatus STATUS_TO_UPDATE_TO = EnumStatus.DONE;
    @Mock
    private TaskRepository repository;

    @Test
    void testRegisterTask() {
        doReturn(getTask()).when(repository).save(TaskMapper.fromFormToEntity(getTaskForm()));
        TaskResponse registering = service.registerTask(DESCRIPTION, STATUS, PRIORITY);
        assertNotNull(registering);
        verify(repository).save(TaskMapper.fromFormToEntity(getTaskForm()));
    }

    @Test
    void testRegisterTaskWithoutDescription() {
        doThrow(ConstraintViolationException.class).when(repository).save(TaskMapper.fromFormToEntity(getTaskFormWithoutDescription()));
        Exception exception = assertThrows(Exception.class, () -> service.registerTask("", STATUS, PRIORITY));
        assertNotNull(exception);
    }

    @Test
    void testRegisterTaskException() {
        doThrow(RequestException.class).when(repository).save(TaskMapper.fromFormToEntity(getTaskForm()));
        Exception exception = assertThrows(Exception.class, () -> service.registerTask(DESCRIPTION, STATUS, PRIORITY));
        assertNotNull(exception);
    }

    @Test
    void testUpdateTaskStatus() {
        doReturn(getTaskOptional()).when(repository).findById(ID);
        TaskResponse updating = service.updateTaskStatus(ID, STATUS_TO_UPDATE_TO);
        assertEquals(STATUS_TO_UPDATE_TO, updating.getStatus());
        verify(repository).findById(ID);
    }

    @Test
    void testUpdateTaskStatusWithUnexistentId() {
        doThrow(ResourceNotFoundException.class).when(repository).findById(ID);
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> service.updateTaskStatus(ID, STATUS));
        assertEquals("Task not found.", exception.getMessage());
    }

    @Test
    void testUpdateTaskStatusException() {
        doThrow(RequestException.class).when(repository).findById(ID);
        Exception exception = assertThrows(RequestException.class, () -> service.updateTaskStatus(ID, STATUS));
        assertEquals("Error updating task's status. null", exception.getMessage());
    }

    @Test
    void testDeleteTask() {
        doReturn(getTaskOptional()).when(repository).findById(ID);
        service.deleteTask(ID);
        verify(repository).findById(ID);
    }

    @Test
    void testDeleteTaskWithUnexistentId() {
        doThrow(ResourceNotFoundException.class).when(repository).findById(ID);
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> service.deleteTask(ID));
        assertEquals("Task not found.", exception.getMessage());
    }

    @Test
    void testDeleteTaskException() {
        doThrow(RequestException.class).when(repository).findById(ID);
        Exception exception = assertThrows(RequestException.class, () -> service.deleteTask(ID));
        assertEquals("Error deleting task. null", exception.getMessage());
    }

    private TaskForm getTaskForm() {
        return TaskForm.builder()
                .description(DESCRIPTION)
                .status(STATUS)
                .priority(PRIORITY)
                .build();
    }

    private TaskForm getTaskFormWithoutDescription() {
        return TaskForm.builder()
                .description("")
                .status(STATUS)
                .priority(PRIORITY)
                .build();
    }

    private Task getTask() {
        return Task.builder()
                .id(ID)
                .createdAt(LocalDate.now())
                .description(DESCRIPTION)
                .status(STATUS)
                .priority(PRIORITY)
                .build();
    }

    private Optional<Task> getTaskOptional() {
        return Optional.of(Task.builder()
                .id(ID)
                .createdAt(LocalDate.now())
                .description(DESCRIPTION)
                .status(STATUS)
                .priority(PRIORITY)
                .build());
    }
}
