package com.springboot.scheduling.repositories;

import com.springboot.jooq.tables.pojos.Task;
import com.springboot.jooq.tables.records.TaskRecord;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.springboot.jooq.tables.Task.TASK;

@Description(value = "Repository layer for task scheduling")
@Repository
public class TaskScheduleRepository {

    private DSLContext dslContext;

    /**
     * Constructor / dependency injector.
     * @param dslContext - jooq context layer.
     */
    public TaskScheduleRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    /**
     * Method for registering scheduled task.
     *
     * @param task - scheduled task to be registered.
     * @return inserted Task object.
     */
    public Task registerScheduledTask(Task task)
    {
        TaskRecord taskRecord = dslContext
                .insertInto(TASK, TASK.NAME)
                .values(task.getName())
                .returning()
                .fetchOne();

        task.setId(taskRecord.getId());
        return task;
    }

    /**
     * Method for getting all registered tasks.
     *
     * @return List of Task objects.
     */
    public List<Task> findAll()
    {
        return dslContext
                .select(TASK.ID, TASK.NAME, TASK.EXECUTED, TASK.EXECUTED_TIME)
                .from(TASK)
                .fetchInto(Task.class);
    }
}
