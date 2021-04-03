package com.marcuschiu.springboot.quartzexample.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static com.marcuschiu.springboot.quartzexample.quartz.SpringScheduledConfig.MY_SCHEDULED_NAME_PREFIX;

@RestController
public class DefaultController2 {

    @Autowired @Lazy
    ScheduledTaskRegistrar scheduledTaskRegistrar;

    @GetMapping("/num-active")
    public Integer getActiveScheduledCount() {
        ThreadPoolTaskScheduler taskScheduler = (ThreadPoolTaskScheduler) scheduledTaskRegistrar.getScheduler();
        return taskScheduler.getActiveCount();
    }

    /**
     * @return Status of DefaultScheduled.scheduled()
     */
    @GetMapping("/status")
    public String getRunningScheduledThreads() {
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread : threadSet) {
            if (thread.getName().startsWith(MY_SCHEDULED_NAME_PREFIX)) {
                if (isIn(thread)) {
                    return "is running";
                }
            }
        }
        return "not running";
    }

    public boolean isIn(Thread thread) {
        for (StackTraceElement s : thread.getStackTrace()) {
            if (s.toString().contains("com.marcuschiu.springboot.quartzexample.scheduled.DefaultScheduled.scheduled"))
                return true;
        }
        return false;
    }
}
