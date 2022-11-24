package com.task.list.json;

import com.task.list.entity.EnumPriority;
import com.task.list.entity.EnumStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskForm {
    private String description;
    private EnumStatus status;
    private EnumPriority priority;
}
