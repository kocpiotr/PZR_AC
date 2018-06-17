package com.pzr.adminconsole.bootstrap;

import com.pzr.adminconsole.entities.*;
import com.pzr.adminconsole.entities.address.Address;
import com.pzr.adminconsole.entities.address.City;
import com.pzr.adminconsole.entities.address.District;
import com.pzr.adminconsole.entities.enums.TimeOfDayEnum;
import com.pzr.adminconsole.entities.process.ProcessInstance;
import com.pzr.adminconsole.entities.process.ProcessStepMatrix;
import com.pzr.adminconsole.entities.process.ProcessTemplate;
import com.pzr.adminconsole.entities.process.StepInstance;
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
    private ProcessStepMatrixRepository processStepMatrixRepository;
    private ProcessTemplateRepository processTemplateRepository;
    private ProcessService processService;
    private ProcessInstanceRepository processInstanceRepository;
    
    public bootstrap(SpecializationRepository specializationRepository, CityRepository cityRepository, DistrictRepository districtRepository,
            OrderRepository orderRepository, AddressRepository addressRepository, ProcessStepMatrixRepository processStepMatrixRepository,
            ProcessTemplateRepository processTemplateRepository, ProcessService processService, ProcessInstanceRepository processInstanceRepository) {
        super();
        this.specializationRepository = specializationRepository;
        this.cityRepository = cityRepository;
        this.districtRepository = districtRepository;
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
        this.processStepMatrixRepository = processStepMatrixRepository;
        this.processTemplateRepository = processTemplateRepository;
        this.processService = processService;
        this.processInstanceRepository = processInstanceRepository;
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
        ProcessTemplate process = new ProcessTemplate();
        List<ProcessStepMatrix> steps = new ArrayList<>();
        steps.add(ProcessStepMatrix.builder().prevStep(null).currentStep("Nowe")
                .processTemplate(process).build());
        steps.add(ProcessStepMatrix.builder().prevStep("Nowe").currentStep("Potwierdzone")
                .processTemplate(process).build());
        steps.add(ProcessStepMatrix.builder().prevStep("Nowe").currentStep("Anulowane")
                .processTemplate(process).build());
        steps.add(ProcessStepMatrix.builder().prevStep("Potwierdzone").currentStep("Znaleziono Speca")
                .processTemplate(process).build());
        steps.add(ProcessStepMatrix.builder().prevStep("Potwierdzone").currentStep("Anulowane")
                .processTemplate(process).build());
        steps.add(ProcessStepMatrix.builder().prevStep("Anulowane").currentStep("Nowe")
                .processTemplate(process).build());
        steps.add(ProcessStepMatrix.builder().prevStep("Znaleziono Speca").currentStep("Oczekuje na porozumienie")
                .processTemplate(process).build());
        steps.add(ProcessStepMatrix.builder().prevStep("Znaleziono Speca").currentStep("Anulowane")
                .processTemplate(process).build());
        steps.add(ProcessStepMatrix.builder().prevStep("Oczekuje na porozumienie").currentStep("Oczekuje na wizyte")
                .processTemplate(process).build());
        steps.add(ProcessStepMatrix.builder().prevStep("Oczekuje na porozumienie").currentStep("Anulowane")
                .processTemplate(process).build());
        steps.add(ProcessStepMatrix.builder().prevStep("Oczekuje na wizyte").currentStep("Oczekuje na ankiete")
                .processTemplate(process).build());
        steps.add(ProcessStepMatrix.builder().prevStep("Oczekuje na wizyte").currentStep("Anulowane")
                .processTemplate(process).build());
        steps.add(ProcessStepMatrix.builder().prevStep("Oczekuje na ankiete").currentStep("Zakonczone")
                .processTemplate(process).build());

        process.setVersion("v0.01");
        process.setActive(true);

        processTemplateRepository.save(process);
        processStepMatrixRepository.saveAll(steps);

    }

    private void initializeOrders() {
        final Orderr zlecenie_01 = new Orderr();
        final Orderr zlecenie_02 = new Orderr();
        final Orderr zlecenie_03 = new Orderr();
        
        final ProcessInstance process_01 = processService.createProcess();
        final ProcessInstance process_02 = processService.createProcess();
        final ProcessInstance process_03 = processService.createProcess();

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
        zlecenie_01.setProcess(process_01);

        zlecenie_02.setSpecjalista(specializationRepository.findByName("Hydraulik"));
        zlecenie_02.setAddress(address);
        zlecenie_02.setPhoneNumber("515079676");
        zlecenie_02.setDescription("Wymiana spłuczki 2");
        zlecenie_02.setCreationDate(LocalDateTime.now());
        zlecenie_02.setServiceDate(LocalDate.now());
        zlecenie_02.setServiceTimeOfDay(TimeOfDayEnum.AFTER_LUNCH);
        zlecenie_02.setProcess(process_02);

        zlecenie_03.setSpecjalista(specializationRepository.findByName("Hydraulik"));
        zlecenie_03.setAddress(address);
        zlecenie_03.setPhoneNumber("515079676");
        zlecenie_03.setDescription("Wymiana spłuczki 3");
        zlecenie_03.setCreationDate(LocalDateTime.now());
        zlecenie_03.setServiceDate(LocalDate.now());
        zlecenie_03.setServiceTimeOfDay(TimeOfDayEnum.EVENING);
        zlecenie_03.setProcess(process_03);

        addressRepository.save(address);

        processInstanceRepository.save(process_01);
        processInstanceRepository.save(process_02);
        processInstanceRepository.save(process_03);
        
        orderRepository.save(zlecenie_01);
        orderRepository.save(zlecenie_02);
        orderRepository.save(zlecenie_03);

        processService.pushForward(zlecenie_02.getProcess(), "Potwierdzone");
        processService.pushForward(zlecenie_03.getProcess(), "Potwierdzone");
        processService.pushForward(zlecenie_03.getProcess(), "Znaleziono Speca");

    }
}
