package com.pzr.adminconsole.entities.address;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class City {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public City(String name) {
        this.name = name;
    }
}
