package com.task.list.repository;

import com.task.list.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("select t from TASK t where status != 3")
    List<Task> findAllTasksButDoneOnes();
}
