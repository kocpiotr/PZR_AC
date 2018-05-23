package com.pzr.adminconsole.repositories;

import com.pzr.adminconsole.entities.District;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface DistrictRepository extends CrudRepository<District, Long> {
    District findByName(String name);
    Set<District> findAllByCityName(String cityName);
}
