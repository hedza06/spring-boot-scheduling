package com.springboot.scheduling.services;

import com.springboot.jooq.tables.pojos.Task;
import com.springboot.scheduling.repositories.TaskScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Description;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Description(value = "Task Schedule Service")
@Service
public class TaskScheduleService {

    private final Logger LOGGER = LoggerFactory.getLogger(TaskScheduleService.class);

    private TaskScheduleRepository taskScheduleRepository;

    /**
     * Constructor / dependency injector.
     * @param taskScheduleRepository - repository layer dependency.
     */
    public TaskScheduleService(TaskScheduleRepository taskScheduleRepository) {
        this.taskScheduleRepository = taskScheduleRepository;
    }


    @Scheduled(fixedRate = 10000)
    public void taskWithFixedRate()
    {
        LOGGER.info("Task with fixed rate called.");
        // do something...

        // generate schedule task
        Task task = new Task();
        task.setName("fixed_rate_task");

        // check inserted task.
        Task insertedTask = this.taskScheduleRepository.registerScheduledTask(task);
        if (insertedTask == null) {
            LOGGER.error("Task with fixed rate can not be registered.");
        }
    }

    @Scheduled(fixedDelay = 5000)
    public void taskWithFixedDelay()
    {
        LOGGER.info("Task with delay...");
    }

    @Scheduled(cron = "1 * * * * *")
    public void taskWithCronExpression()
    {
        /*
        |---------------------------------------------------------------|
        |   0 - 59      (min)
        |   0 - 23      (hour)
        |   1 - 31      (day of month)
        |   1 - 12      (month)
        |   1 - 7       (day of week)
        |   1900 - 3000 (year)
        |---------------------------------------------------------------|
        */
        LOGGER.info("Task every minute triggered");

        // do something...

        // generate task
        Task task = new Task();
        task.setName("every_minute_task");

        // check if is registered.
        Task insertedTask = this.taskScheduleRepository.registerScheduledTask(task);
        if (insertedTask == null) {
            LOGGER.error("Task with every minute execution can not be registered.");
        }
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void taskOneMonthly()
    {
        LOGGER.info("One monthly task executed.");

        // do something...

        // generate task
        Task task = new Task();
        task.setName("every_month_task");

        // check if is registered.
        Task insertedTask = this.taskScheduleRepository.registerScheduledTask(task);
        if (insertedTask == null) {
            LOGGER.error("Monthly task execution can not be registered.");
        }
    }
}
