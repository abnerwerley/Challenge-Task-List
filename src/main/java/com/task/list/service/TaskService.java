package com.task.list.service;

import com.task.list.entity.Task;
import com.task.list.entity.mapper.TaskMapper;
import com.task.list.exception.RequestException;
import com.task.list.json.TaskForm;
import com.task.list.json.TaskResponse;
import com.task.list.json.mapper.TaskResponseMapper;
import com.task.list.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public TaskResponse registerTask(TaskForm taskForm) {
        try {
            Task task = TaskMapper.fromFormToEntity(taskForm);
            return TaskResponseMapper.fromEntityToResponse(repository.save(task));
        } catch (Exception e) {
            log.error("Error when registering new task.");
            e.printStackTrace();
            throw new RequestException(HttpStatus.BAD_REQUEST, "Error when registering new task.");
        }
    }
}
