package com.pzr.adminconsole.entities;

import com.pzr.adminconsole.entities.address.Address;
import com.pzr.adminconsole.entities.enums.TimeOfDayEnum;
import com.pzr.adminconsole.entities.process.ManagingProcess;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Orderr {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Specialization specjalista;

    @ManyToOne
    private Address address;

    private String description;
    private String phoneNumber;
    private LocalDate serviceDate;

    @Enumerated(EnumType.STRING)
    private TimeOfDayEnum serviceTimeOfDay;
    private LocalDateTime creationDate = LocalDateTime.now();

    @OneToOne(cascade = CascadeType.ALL)
    private ManagingProcess process;

    //-------------------------------------CLEVER METHODS----------------------------------------


}
