package com.task.list.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity(name = "TASK")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Description must no be blank.")
    private String description;

    @Column(updatable = false)
//    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDate createdAt;

    @NotBlank(message = "Status must no be blank.")
    private EnumStatus status;

    @NotBlank(message = "Priority must no be blank.")
    private EnumPriority priority;
}
