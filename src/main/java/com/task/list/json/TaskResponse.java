package com.task.list.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.task.list.entity.EnumPriority;
import com.task.list.entity.EnumStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
    private Long id;
    private String description;

//    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDate createdAt;
    private EnumStatus status;
    private EnumPriority priority;
}
