package com.pzr.adminconsole.bootstrap;

import com.pzr.adminconsole.entities.*;
import com.pzr.adminconsole.entities.address.Address;
import com.pzr.adminconsole.entities.address.City;
import com.pzr.adminconsole.entities.address.District;
import com.pzr.adminconsole.entities.enums.TimeOfDayEnum;
import com.pzr.adminconsole.entities.process.ManagingProcess;
import com.pzr.adminconsole.entities.process.Step;
import com.pzr.adminconsole.entities.process.enums.StageTypeEnum;
import com.pzr.adminconsole.repositories.*;
import com.pzr.adminconsole.services.ProcessService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private SpecializationRepository specializationRepository;
    private CityRepository cityRepository;
    private DistrictRepository districtRepository;
    private OrderRepository orderRepository;
    private AddressRepository addressRepository;
    private StepRepository stepRepository;
    private ManagingProcessRepository managingProcessRepository;
    private ProcessService processService;

    public bootstrap(SpecializationRepository specializationRepository, CityRepository cityRepository, DistrictRepository districtRepository, OrderRepository orderRepository, AddressRepository addressRepository, StepRepository stepRepository, ManagingProcessRepository managingProcessRepository, ProcessService processService) {
        this.specializationRepository = specializationRepository;
        this.cityRepository = cityRepository;
        this.districtRepository = districtRepository;
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
        this.stepRepository = stepRepository;
        this.managingProcessRepository = managingProcessRepository;
        this.processService = processService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initializeSpecjalizations();
        initializeCities();
        initializeDistricts();
        initializeAddresses();
        initializeProcessTemplate();
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

    private void initializeProcessTemplate() {
        ManagingProcess process = new ManagingProcess();
        List<Step> stepsToPersist = new ArrayList<>();
        process.setVersionName("v0.01");
        Step step_001 = new Step().withProcess(process).withType(StageTypeEnum.NOWE);
        Step step_002 = new Step().withProcess(process).withType(StageTypeEnum.POTWIERDZONO).withPrev(step_001);
        Step step_003 = new Step().withProcess(process).withType(StageTypeEnum.ZNALEZIONO_SPECA).withPrev(step_002);
        Step step_004 = new Step().withProcess(process).withType(StageTypeEnum.OCZEKUJE_NA_POROZUMIENIE).withPrev(step_003);
        Step step_005 = new Step().withProcess(process).withType(StageTypeEnum.OCZEKUJE_NA_WIZYTE).withPrev(step_004);
        Step step_006 = new Step().withProcess(process).withType(StageTypeEnum.OCZEKUJE_NA_ANKIETE).withPrev(step_005);
        Step step_007 = new Step().withProcess(process).withType(StageTypeEnum.ZAKONCZONE).withPrev(step_006);
        step_001.setNext(step_002);
        step_002.setNext(step_003);
        step_003.setNext(step_004);
        step_004.setNext(step_005);
        step_005.setNext(step_006);
        step_006.setNext(step_007);

        stepsToPersist.add(step_001);
        stepsToPersist.add(step_002);
        stepsToPersist.add(step_003);
        stepsToPersist.add(step_004);
        stepsToPersist.add(step_005);
        stepsToPersist.add(step_006);
        stepsToPersist.add(step_007);

        process.setInitialStep(step_001);

        managingProcessRepository.save(process);
        stepRepository.saveAll(stepsToPersist);

    }

    private void initializeOrders() {
        Orderr zlecenie_01 = new Orderr();
        Orderr zlecenie_02 = new Orderr();
        Orderr zlecenie_03 = new Orderr();

        Address address = new Address();
        address.setCity(cityRepository.findByName("Elbląg"));
        address.setDistrict(districtRepository.findByName("Zawada"));
        address.setStreet("Pokorna");

        zlecenie_01.setSpecjalista(specializationRepository.findByName("Hydraulik"));
        zlecenie_01.setAddress(address);
        zlecenie_01.setPhoneNumber("515079676");
        zlecenie_01.setDescription("Wymiana spłuczki 1");
        zlecenie_01.setCreationDate(LocalDateTime.now());
        zlecenie_01.setServiceDate(LocalDate.now());
        zlecenie_01.setServiceTimeOfDay(TimeOfDayEnum.BEFORE_LUNCH);
        zlecenie_01.setProcess(processService.createProcessInstance(managingProcessRepository.findByVersionName("v0.01")));

        zlecenie_02.setSpecjalista(specializationRepository.findByName("Hydraulik"));
        zlecenie_02.setAddress(address);
        zlecenie_02.setPhoneNumber("515079676");
        zlecenie_02.setDescription("Wymiana spłuczki 2");
        zlecenie_02.setCreationDate(LocalDateTime.now());
        zlecenie_02.setServiceDate(LocalDate.now());
        zlecenie_02.setServiceTimeOfDay(TimeOfDayEnum.AFTER_LUNCH);
        zlecenie_02.setProcess(processService.createProcessInstance(managingProcessRepository.findByVersionName("v0.01")));


        zlecenie_03.setSpecjalista(specializationRepository.findByName("Hydraulik"));
        zlecenie_03.setAddress(address);
        zlecenie_03.setPhoneNumber("515079676");
        zlecenie_03.setDescription("Wymiana spłuczki 3");
        zlecenie_03.setCreationDate(LocalDateTime.now());
        zlecenie_03.setServiceDate(LocalDate.now());
        zlecenie_03.setServiceTimeOfDay(TimeOfDayEnum.EVENING);
        zlecenie_03.setProcess(processService.createProcessInstance(managingProcessRepository.findByVersionName("v0.01")));

        addressRepository.save(address);

        orderRepository.save(zlecenie_01);
        orderRepository.save(zlecenie_02);
        orderRepository.save(zlecenie_03);

        processService.pushForward(zlecenie_02.getProcess());
        processService.pushForward(zlecenie_03.getProcess());
        processService.pushForward(zlecenie_03.getProcess());


    }
}
