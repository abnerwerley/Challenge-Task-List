package com.task.list.json.mapper;

import com.task.list.entity.Task;
import com.task.list.json.TaskResponse;

public class TaskResponseMapper {

    public static TaskResponse fromEntityToResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .createdAt(task.getCreatedAt())
                .build();
    }

    private TaskResponseMapper(){}
}
