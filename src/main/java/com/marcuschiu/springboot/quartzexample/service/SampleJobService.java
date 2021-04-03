package com.marcuschiu.springboot.quartzexample.service;

import org.springframework.stereotype.Service;

import static java.lang.Thread.sleep;

@Service
public class SampleJobService {
    public void executeSampleJob() {
        System.out.println("STARTED");
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ENDED");
    }
}
