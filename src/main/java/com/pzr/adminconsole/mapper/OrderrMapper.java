package com.pzr.adminconsole.mapper;

import com.pzr.adminconsole.entities.Orderr;

public class OrderrMapper {
    public static void map(Orderr target, Orderr src) {
        target.setDescription(src.getDescription());
        target.setPhoneNumber(src.getPhoneNumber());
        target.setServiceDate(src.getServiceDate());
        target.setServiceTimeOfDay(src.getServiceTimeOfDay());
        target.setSpecjalista(src.getSpecjalista());

        AddressMapper.map(target.getAddress(), src.getAddress());
    }
}
