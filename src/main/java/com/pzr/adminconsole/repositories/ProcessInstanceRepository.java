package com.pzr.adminconsole.repositories;

import org.springframework.data.repository.CrudRepository;

import com.pzr.adminconsole.entities.process.ProcessInstance;

public interface ProcessInstanceRepository extends CrudRepository<ProcessInstance, Long> {
}
