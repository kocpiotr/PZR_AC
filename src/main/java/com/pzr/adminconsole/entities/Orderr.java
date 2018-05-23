package com.pzr.adminconsole.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Orderr {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Specialization specjalista;

    @ManyToOne(cascade = CascadeType.ALL)
    private Address address;

    private String description;
    private String phoneNumber;
    private LocalDateTime date;
}
