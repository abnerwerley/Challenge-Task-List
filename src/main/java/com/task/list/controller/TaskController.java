package com.task.list.controller;

import com.task.list.entity.EnumPriority;
import com.task.list.entity.EnumStatus;
import com.task.list.json.TaskResponse;
import com.task.list.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
@AllArgsConstructor
public class TaskController {

    @Autowired
    private TaskService service;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("")
    TaskResponse registerTask(@RequestParam String description,
                              @RequestParam(defaultValue = "PENDING") EnumStatus status,
                              @RequestParam(defaultValue = "LOW") EnumPriority priority) {
        return service.registerTask(description, status, priority);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/status")
    TaskResponse updateTaskStatus(@RequestParam Long id,
                                  @RequestParam EnumStatus status) {
        return service.updateTaskStatus(id, status);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void deleteTask(@PathVariable Long id) {
        service.deleteTask(id);
    }

}
