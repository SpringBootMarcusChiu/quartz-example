package com.marcuschiu.springboot.quartzexample.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DefaultScheduled {

    @Scheduled(cron = "*/10 * * * * *")
    public void scheduled() throws InterruptedException {
        System.out.println("STARTING");
        Thread.sleep(5000);
        System.out.println("ENDING");
    }
}
