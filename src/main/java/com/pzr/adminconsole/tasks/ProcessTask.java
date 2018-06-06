package com.pzr.adminconsole.tasks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProcessTask {

    @Scheduled(fixedRate = 5000 )
    public void createReminders() {
        //for each orderr which is not closed
        //get current process
        //check if there is open reminder linked to step
        //if not then create one
    }
}
