package com.pzr.adminconsole.controllers;

import com.pzr.adminconsole.entities.address.District;
import com.pzr.adminconsole.repositories.DistrictRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class RestDistrictController {

    private DistrictRepository districtRepository;

    public RestDistrictController(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    @RequestMapping("/districts/{city}")
    public Set<District> getDistrictsForGivenCity(@PathVariable("city") String city) {
        return districtRepository.findAllByCityName(city);
    }
}
