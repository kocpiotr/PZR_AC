package com.pzr.adminconsole.repositories;

import org.springframework.data.repository.CrudRepository;

import com.pzr.adminconsole.entities.process.StepInstance;

public interface StepInstanceRepository extends CrudRepository<StepInstance, Long> {
}
