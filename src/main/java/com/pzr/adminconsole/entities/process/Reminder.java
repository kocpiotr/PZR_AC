package com.pzr.adminconsole.entities.process;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    @OneToOne
    private ManagingProcess status;
}
