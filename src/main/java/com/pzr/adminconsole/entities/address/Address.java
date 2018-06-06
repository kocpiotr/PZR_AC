package com.pzr.adminconsole.entities.address;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private City city;

    @ManyToOne
    private District district;

    private String street;

    private String houseNumber;
}
