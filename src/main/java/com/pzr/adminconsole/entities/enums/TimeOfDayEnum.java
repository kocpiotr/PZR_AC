package com.pzr.adminconsole.entities.enums;

public enum TimeOfDayEnum {
    MORNING("Rano"), BEFORE_LUNCH("Przed Południem"), AFTER_LUNCH("Po Południu"), EVENING("Wieczorem"), NOW("Teraz");

    String name;
    TimeOfDayEnum(final String name) {
        this.name = name;
    }
}
