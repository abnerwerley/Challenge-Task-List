package com.task.list.entity.mapper;

import com.task.list.entity.Task;
import com.task.list.json.TaskForm;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskMapper {

    public static Task fromFormToEntity(TaskForm form) {
        return Task.builder()
                .id(form.getId())
                .description(form.getDescription())
                .status(form.getStatus())
                .priority(form.getPriority())
                .createdAt(LocalDate.now())
                .build();
    }

    public TaskMapper(){}
}
