package com.pzr.adminconsole.repositories;

import com.pzr.adminconsole.entities.address.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {

}
