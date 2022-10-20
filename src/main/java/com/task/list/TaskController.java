package com.task.list;

import com.task.list.entity.Task;
import com.task.list.json.TaskForm;
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
    @PutMapping("")
    TaskResponse registerTask(@RequestBody TaskForm form) {
        return service.registerTask(form);
    }
}
