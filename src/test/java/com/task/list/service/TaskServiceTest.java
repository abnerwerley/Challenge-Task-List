package com.task.list.service;

import com.task.list.entity.EnumPriority;
import com.task.list.entity.EnumStatus;
import com.task.list.entity.Task;
import com.task.list.entity.mapper.TaskMapper;
import com.task.list.json.TaskForm;
import com.task.list.json.TaskResponse;
import com.task.list.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @InjectMocks
    private TaskService service;

    public static final Long ID = 1L;
    public static final String DESCRIPTION = "Cool task to do.";
    public static final EnumStatus STATUS = EnumStatus.PENDING;

    public static final EnumPriority PRIORITY = EnumPriority.LOW;

    @Mock
    private TaskRepository repository;

    @Test
    void testRegisterTask() {
        doReturn(getTask()).when(repository).save(TaskMapper.fromFormToEntity(getTaskForm()));
        TaskResponse registering = service.registerTask(getTaskForm());
        assertNotNull(registering);
        verify(repository).save(TaskMapper.fromFormToEntity(getTaskForm()));
    }

    private TaskForm getTaskForm() {
        return TaskForm.builder()
                .id(ID)
                .description(DESCRIPTION)
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
}
