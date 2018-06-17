package com.pzr.adminconsole.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pzr.adminconsole.entities.process.ProcessInstance;
import com.pzr.adminconsole.entities.process.ProcessStepMatrix;
import com.pzr.adminconsole.entities.process.ProcessTemplate;
import com.pzr.adminconsole.entities.process.StepInstance;
import com.pzr.adminconsole.repositories.ProcessInstanceRepository;
import com.pzr.adminconsole.repositories.ProcessStepMatrixRepository;
import com.pzr.adminconsole.repositories.ProcessTemplateRepository;
import com.pzr.adminconsole.repositories.StepInstanceRepository;

@Service
public class ProcessInstanceServiceImpl implements ProcessService {

    private ProcessTemplateRepository processTemplateRepository;
    private ProcessStepMatrixRepository processStepMatrixRepository;
    private StepInstanceRepository stepinstanceRepository;
    private ProcessInstanceRepository processInstanceRepository;

    public ProcessInstanceServiceImpl(ProcessTemplateRepository processTemplateRepository, ProcessStepMatrixRepository processStepMatrixRepository,
            StepInstanceRepository stepinstanceRepository, ProcessInstanceRepository processInstanceRepository) {
        super();
        this.processTemplateRepository = processTemplateRepository;
        this.processStepMatrixRepository = processStepMatrixRepository;
        this.stepinstanceRepository = stepinstanceRepository;
        this.processInstanceRepository = processInstanceRepository;
    }

    @Override
    public ProcessInstance createProcess() {
        final ProcessTemplate processTemplate = processTemplateRepository.findByActiveTrue();
        final ProcessStepMatrix processStepMatrixItem = processStepMatrixRepository.findByProcessTemplateAndPrevStep(processTemplate, null);
        final ProcessInstance processInstance = ProcessInstance.builder().processTemplate(processTemplate).build();
        final StepInstance stepInstance = StepInstance.builder().name(processStepMatrixItem.getCurrentStep())
            .previousStep(null).processInstance(processInstance).build();
        
        processInstance.setCurrentStep(stepInstance);
        
        processInstanceRepository.save(processInstance);
        stepinstanceRepository.save(stepInstance);
        
        return processInstance;
    }

    @Override
    public List<String> getAvailableSteps(ProcessInstance processInstance) {
         final List<ProcessStepMatrix> allAvailableNextSteps = processStepMatrixRepository.findAllByPrevStep(processInstance.getCurrentStep().getName());
         final List<String> nextStepsNames = allAvailableNextSteps.stream().map(ProcessStepMatrix::getCurrentStep).collect(Collectors.toList());
         return nextStepsNames;
    }

    @Override
    public void pushForward(ProcessInstance processInstance, String nextStep) {
        final List<String> availableSteps = getAvailableSteps(processInstance);
        if (!availableSteps.contains(nextStep)) {
            throw new IllegalArgumentException("Next step: " + nextStep + " is not legal for given process");
        }
        
        final StepInstance currentStepInstance = processInstance.getCurrentStep();
        final StepInstance nextStepInstance = StepInstance.builder().name(nextStep)
                .previousStep(currentStepInstance).processInstance(processInstance).build();
        
        processInstance.setCurrentStep(nextStepInstance);
        stepinstanceRepository.save(nextStepInstance);
        processInstanceRepository.save(processInstance);
        
    }

}
