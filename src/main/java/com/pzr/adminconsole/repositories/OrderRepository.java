package com.pzr.adminconsole.repositories;

import com.pzr.adminconsole.entities.Orderr;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Orderr, Long> {

}
