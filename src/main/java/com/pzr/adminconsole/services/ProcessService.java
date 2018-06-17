package com.pzr.adminconsole.services;

import java.util.List;

import com.pzr.adminconsole.entities.process.ProcessInstance;

public interface ProcessService {
    ProcessInstance createProcess();
    List<String> getAvailableSteps(ProcessInstance processInstance);
    void pushForward(ProcessInstance processInstance, String nextStep);
    
}
