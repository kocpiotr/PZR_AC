package com.pzr.adminconsole.repositories;

import com.pzr.adminconsole.entities.City;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Long> {
    City findByName(String name);
}
