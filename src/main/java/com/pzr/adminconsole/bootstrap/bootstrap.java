package com.pzr.adminconsole.bootstrap;

import com.pzr.adminconsole.entities.*;
import com.pzr.adminconsole.repositories.*;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
public class bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private SpecializationRepository specializationRepository;
    private CityRepository cityRepository;
    private DistrictRepository districtRepository;
    private OrderRepository orderRepository;
    private AddressRepository addressRepository;

    public bootstrap(SpecializationRepository specializationRepository, CityRepository cityRepository, DistrictRepository districtRepository, OrderRepository orderRepository, AddressRepository addressRepository) {
        this.specializationRepository = specializationRepository;
        this.cityRepository = cityRepository;
        this.districtRepository = districtRepository;
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initializeSpecjalizations();
        initializeCities();
        initializeDistricts();
        initializeAddresses();
        initializeOrders();

        Set<District> pasłęk = districtRepository.findAllByCityName("Pasłęk");
        System.out.println("asdasd");
    }

    private void initializeSpecjalizations() {
        Specialization hydraulik = new Specialization("Hydraulik");
        Specialization kafelkarz = new Specialization("Kafelkarz");

        specializationRepository.save(hydraulik);
        specializationRepository.save(kafelkarz);
    }

    private void initializeCities() {
        City elblag = new City("Elbląg");
        City pasłęk = new City("Pasłęk");

        cityRepository.save(elblag);
        cityRepository.save(pasłęk);
    }

    private void initializeDistricts() {
        District zawada = new District("Zawada");
        zawada.setCity(cityRepository.findByName("Elbląg"));

        District pasłęk_południe = new District("Pasłęk Poludnie");
        pasłęk_południe.setCity(cityRepository.findByName("Pasłęk"));


        districtRepository.save(zawada);
        districtRepository.save(pasłęk_południe);

    }

    private void initializeAddresses() {
        Address address_01 = new Address();
        address_01.setCity(cityRepository.findByName("Elbląg"));
        address_01.setDistrict(districtRepository.findByName("Zawada"));
        address_01.setStreet("Obronców Pokoju");
        addressRepository.save(address_01);
    }

    private void initializeOrders() {
        Orderr zlecenie_01 = new Orderr();

        Address address = new Address();
        address.setCity(cityRepository.findByName("Elbląg"));
        address.setDistrict(districtRepository.findByName("Zawada"));
        address.setStreet("Pokorna");

        zlecenie_01.setSpecjalista(specializationRepository.findByName("Hydraulik"));
        zlecenie_01.setAddress(address);
        zlecenie_01.setPhoneNumber("515079676");
        zlecenie_01.setDescription("Wymiana spłuczki");
        zlecenie_01.setDate(LocalDateTime.now());

        orderRepository.save(zlecenie_01);
        addressRepository.save(address);
    }
}
