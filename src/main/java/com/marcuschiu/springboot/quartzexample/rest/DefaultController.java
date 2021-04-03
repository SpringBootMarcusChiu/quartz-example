package com.marcuschiu.springboot.quartzexample.rest;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DefaultController {

    @Autowired
    Scheduler scheduler;

    @GetMapping("/")
    public Boolean getAllRunningJobs() throws SchedulerException {
        getCurrentJobs();
        return isJobPaused("Qrtz_Job_Detail");
    }

    private List<JobExecutionContext> getCurrentJobs() throws SchedulerException {
        return scheduler.getCurrentlyExecutingJobs();
    }

    private Boolean isJobPaused(String jobName) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());
        for (Trigger trigger : triggers) {
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            System.out.println(triggerState);
            if (Trigger.TriggerState.PAUSED.equals(triggerState)) {
                return true;
            }
        }
        return false;
    }
}
