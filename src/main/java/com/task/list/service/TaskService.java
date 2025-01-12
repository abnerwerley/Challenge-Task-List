package com.task.list.service;

import com.task.list.entity.EnumPriority;
import com.task.list.entity.EnumStatus;
import com.task.list.entity.Task;
import com.task.list.entity.mapper.TaskMapper;
import com.task.list.exception.RequestException;
import com.task.list.exception.ResourceNotFoundException;
import com.task.list.json.TaskForm;
import com.task.list.json.TaskResponse;
import com.task.list.json.mapper.TaskResponseMapper;
import com.task.list.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
@Slf4j
@AllArgsConstructor
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public static final String TASK_NOT_FOUND_MESSAGE = "Task not found.";

    public TaskResponse registerTask(String description, EnumStatus status, EnumPriority priority) {
        try {
            TaskForm taskForm = new TaskForm(description, status, priority);
            Task task = TaskMapper.fromFormToEntity(taskForm);
            return TaskResponseMapper.fromEntityToResponse(repository.save(task));
        } catch (Exception e) {
            log.error("Error when registering new task.");
            throw new RequestException(HttpStatus.BAD_REQUEST, "Error when registering new task ");
        }
    }

    public TaskResponse updateTaskStatusToDone(Long id) {
        try {
            Optional<Task> taskOptional = repository.findById(id);
            if (taskOptional.isPresent()) {
                Task task = taskOptional.get();
                task.setStatus(EnumStatus.DONE);
                repository.save(task);
                return TaskResponseMapper.fromEntityToResponse(task);
            }
            throw new ResourceNotFoundException(TASK_NOT_FOUND_MESSAGE);
        } catch (ResourceNotFoundException e) {
            log.error(TASK_NOT_FOUND_MESSAGE);
            throw new ResourceNotFoundException(TASK_NOT_FOUND_MESSAGE);
        } catch (Exception e) {
            log.error("Error updating task's status.");
            throw new RequestException(HttpStatus.BAD_REQUEST, "Error updating task's status.");
        }
    }

    public void deleteTask(Long id) {
        try {
            Optional<Task> taskOptional = repository.findById(id);
            if (taskOptional.isPresent()) {
                repository.deleteById(id);
            } else {
                throw new ResourceNotFoundException(TASK_NOT_FOUND_MESSAGE);
            }
        } catch (ResourceNotFoundException e) {
            log.error(TASK_NOT_FOUND_MESSAGE);
            throw new ResourceNotFoundException(TASK_NOT_FOUND_MESSAGE);
        } catch (Exception e) {
            log.error("Error deleting task.");
            throw new RequestException(HttpStatus.BAD_REQUEST, "Error deleting task.");
        }
    }

    public Stream<TaskResponse> getAllTaskButDoneOnes() {
        try {
            return repository.findAllTasksButDoneOnes().stream().map(TaskResponseMapper::fromEntityToResponse);
        } catch (Exception exception) {
            log.error("Error getting all tasks but done ones. ");
            throw new RequestException(HttpStatus.BAD_REQUEST, "Error getting all tasks but done ones.");
        }
    }

    public Stream<TaskResponse> getAllTasks() {
        try {
            return repository.findAll().stream().map(TaskResponseMapper::fromEntityToResponse);
        } catch (Exception exception) {
            log.error("Error getting all tasks.");
            throw new RequestException(HttpStatus.BAD_REQUEST, "Error getting all tasks.");
        }
    }
}
