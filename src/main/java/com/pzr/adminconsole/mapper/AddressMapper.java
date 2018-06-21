package com.pzr.adminconsole.mapper;

import com.pzr.adminconsole.entities.address.Address;

public class AddressMapper {

    public static void map(Address target, Address src) {
        target.setCity(src.getCity());
        target.setDistrict(src.getDistrict());
        target.setHouseNumber(src.getHouseNumber());
        target.setStreet(src.getStreet());
    }

}
