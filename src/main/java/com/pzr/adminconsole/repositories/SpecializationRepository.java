package com.pzr.adminconsole.repositories;

import com.pzr.adminconsole.entities.Specialization;
import org.springframework.data.repository.CrudRepository;

public interface SpecializationRepository extends CrudRepository<Specialization, Long> {
    Specialization findByName(String name);
}
