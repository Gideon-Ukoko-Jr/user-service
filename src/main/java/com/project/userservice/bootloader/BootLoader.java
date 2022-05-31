package com.project.userservice.bootloader;

import com.project.userservice.services.TestUsersSetupService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@FieldDefaults(makeFinal = true)
@Slf4j
@Component
@AllArgsConstructor
public class BootLoader implements ApplicationListener<ContextRefreshedEvent> {

    private TestUsersSetupService testUsersSetupService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        new Thread(() -> {
            testUsersSetupService.runSetup();
        }).start();
    }
}
