package com.springboot.scheduling.resources;

import com.springboot.jooq.tables.pojos.Task;
import com.springboot.scheduling.repositories.TaskScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/task")
public class TaskScheduleResource {

    private TaskScheduleRepository taskScheduleRepository;

    /**
     * Constructor / dependency injector.
     * @param taskScheduleRepository - task schedule repository layer.
     */
    public TaskScheduleResource(TaskScheduleRepository taskScheduleRepository) {
        this.taskScheduleRepository = taskScheduleRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Task>> getAllTasks()
    {
        List<Task> tasks = taskScheduleRepository.findAll();
        return Optional.ofNullable(tasks)
                .map(taskList -> new ResponseEntity<>(taskList, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
