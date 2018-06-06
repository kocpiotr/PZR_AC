package com.pzr.adminconsole.repositories;

import com.pzr.adminconsole.entities.process.ManagingProcess;
import org.springframework.data.repository.CrudRepository;

public interface ManagingProcessRepository extends CrudRepository<ManagingProcess, Long> {
    ManagingProcess findByVersionName(final String versionName);
}
