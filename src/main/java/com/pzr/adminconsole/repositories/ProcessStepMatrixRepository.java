package com.pzr.adminconsole.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pzr.adminconsole.entities.process.ProcessStepMatrix;
import com.pzr.adminconsole.entities.process.ProcessTemplate;

public interface ProcessStepMatrixRepository extends CrudRepository<ProcessStepMatrix, Long>{
    ProcessStepMatrix findByProcessTemplateAndPrevStep(ProcessTemplate processTemplate, String prevStep);
    List<ProcessStepMatrix> findAllByPrevStep(String prevStep);
}
