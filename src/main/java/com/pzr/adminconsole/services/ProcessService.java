package com.pzr.adminconsole.services;

import com.pzr.adminconsole.entities.process.ManagingProcess;
import com.pzr.adminconsole.entities.process.Step;
import com.pzr.adminconsole.repositories.ManagingProcessRepository;
import com.pzr.adminconsole.repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class ProcessService {

    private ManagingProcessRepository processRepository;

    public ProcessService(ManagingProcessRepository processRepository) {
        this.processRepository = processRepository;
    }

    public ManagingProcess createProcessInstance(ManagingProcess template) {
        if (template == null) {
            throw new IllegalArgumentException("It is not possible to create process instance from NULL template");
        }

        final ManagingProcess instance = new ManagingProcess();
        instance.setTemplate(template);
        cloneProcessSteps(instance);
        instance.setCurrentStep(instance.getInitialStep());

        return instance;
    }

    private void cloneProcessSteps(ManagingProcess instance) {
        final ManagingProcess template = instance.getTemplate();
        final Step initialStep = template.getInitialStep();
        instance.setInitialStep(cloneStepRecuresivelly(instance, null,  initialStep));
    }

    private Step cloneStepRecuresivelly(ManagingProcess process, Step previous, Step stepToCopy) {
        Step cloned = null;

        if (stepToCopy != null) {
            cloned = new Step().withProcess(process).withType(stepToCopy.getType());
            cloned.setPrevious(previous);
            cloned.setNext(cloneStepRecuresivelly(process, cloned, stepToCopy.getNext()));
        }
        return cloned;
    }

    public void pushForward(final ManagingProcess process) {
        if(isInLastStage(process)) {
            throw new RuntimeException("It is not possible to push processs forward, process has been finished");
        }

        Step nextStep = process.getCurrentStep().getNext();
        process.setCurrentStep(nextStep);
        processRepository.save(process);
    }

    public void pushBackward(final ManagingProcess process) {

        if(isInFirstStage(process)) {
            throw new RuntimeException("It is not possible to push processs backward, process is in first step");
        }

        Step prevStep = process.getCurrentStep().getPrevious();
        process.setCurrentStep(prevStep);
        processRepository.save(process);
    }

    private boolean isInFirstStage(ManagingProcess process) {
        return process.getCurrentStep().equals(process.getInitialStep());
    }

    private boolean isInLastStage(ManagingProcess process) {
        return process.getCurrentStep().getNext() == null;
    }
}
