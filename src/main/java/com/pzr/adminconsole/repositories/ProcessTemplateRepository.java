 package com.pzr.adminconsole.repositories;

import org.springframework.data.repository.CrudRepository;

import com.pzr.adminconsole.entities.process.ProcessTemplate;

public interface ProcessTemplateRepository extends CrudRepository<ProcessTemplate, Long> {
    ProcessTemplate findByActiveTrue();
}
