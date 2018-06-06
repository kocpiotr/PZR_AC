package com.pzr.adminconsole.entities.process.enums;

public enum StageTypeEnum {
NOWE(5), POTWIERDZONO(5), ZNALEZIONO_SPECA(15), OCZEKUJE_NA_POROZUMIENIE(15), OCZEKUJE_NA_WIZYTE(300), OCZEKUJE_NA_ANKIETE(100), ZAKONCZONE(5);

    long minutesToFinish = 0;
    StageTypeEnum(long minutesToFinish) {
        this.minutesToFinish = minutesToFinish;
    }
}
