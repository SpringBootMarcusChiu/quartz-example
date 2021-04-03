package com.marcuschiu.springboot.quartzexample.quartz;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class SpringScheduledConfig implements SchedulingConfigurer {

    public static final String MY_SCHEDULED_NAME_PREFIX = "something-here";
    private final int POOL_SIZE = 1;

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
        setThreadNameFormat(threadPoolTaskScheduler);
        threadPoolTaskScheduler.initialize();

        scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
        addToAppContext(scheduledTaskRegistrar);
    }

    private void addToAppContext(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        // add to app context
        ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
        beanFactory.registerSingleton(scheduledTaskRegistrar.getClass().getCanonicalName(), scheduledTaskRegistrar);
    }

    private void setThreadNameFormat(ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        threadPoolTaskScheduler.setThreadNamePrefix(MY_SCHEDULED_NAME_PREFIX);
        // or
//        threadPoolTaskScheduler.setThreadFactory(new ThreadFactoryBuilder().setNameFormat(MY_SCHEDULED_NAME_PREFIX + "-%d").build());
    }
}
