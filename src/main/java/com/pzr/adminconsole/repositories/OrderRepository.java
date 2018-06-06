package com.pzr.adminconsole.repositories;

import com.pzr.adminconsole.entities.Orderr;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrderRepository extends CrudRepository<Orderr, Long> {
    Set<Orderr> findAllByOrderByCreationDateDesc();
    Set<Orderr> findAllByOrderByCreationDateAsc();

}
