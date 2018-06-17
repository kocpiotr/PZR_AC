package com.pzr.adminconsole.controllers;

import com.pzr.adminconsole.entities.Orderr;
import com.pzr.adminconsole.entities.enums.TimeOfDayEnum;
import com.pzr.adminconsole.entities.process.ProcessInstance;
import com.pzr.adminconsole.repositories.AddressRepository;
import com.pzr.adminconsole.repositories.CityRepository;
import com.pzr.adminconsole.repositories.DistrictRepository;
import com.pzr.adminconsole.repositories.OrderRepository;
import com.pzr.adminconsole.repositories.ProcessInstanceRepository;
import com.pzr.adminconsole.repositories.SpecializationRepository;
import com.pzr.adminconsole.services.ProcessService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrderController {

    private OrderRepository orderRepository;
    private SpecializationRepository specializationRepository;
    private CityRepository cityRepository;
    private DistrictRepository districtRepository;
    private AddressRepository addressRepository;
    private ProcessService processService;
    private ProcessInstanceRepository processInstanceRepository;

    
    public OrderController(OrderRepository orderRepository, SpecializationRepository specializationRepository, CityRepository cityRepository,
            DistrictRepository districtRepository, AddressRepository addressRepository, ProcessService processService,
            ProcessInstanceRepository processInstanceRepository) {
        super();
        this.orderRepository = orderRepository;
        this.specializationRepository = specializationRepository;
        this.cityRepository = cityRepository;
        this.districtRepository = districtRepository;
        this.addressRepository = addressRepository;
        this.processService = processService;
        this.processInstanceRepository = processInstanceRepository;
    }

    @RequestMapping("/zlecenia")
    public String mainPage(final Model model) {
        model.addAttribute("availableSpecializations", specializationRepository.findAll());
        model.addAttribute("availableCities", cityRepository.findAll());
        model.addAttribute("availableDistricts", districtRepository.findAll());
        model.addAttribute("existingOrderrs", orderRepository.findAllByOrderByCreationDateAsc());
        model.addAttribute("timesOfDay", TimeOfDayEnum.values());
        model.addAttribute("pusteZlecenie", new Orderr());
        return "zlecenia/glowna";
    }

    @RequestMapping("/dodajZlecenie")
    public ModelAndView addOrder(final @ModelAttribute Orderr orderr) {
    
        ProcessInstance process = processService.createProcess();
        orderr.setProcess(process);
        
        processInstanceRepository.save(process);
        addressRepository.save(orderr.getAddress());
        orderRepository.save(orderr);
        

        return new ModelAndView("redirect:/zlecenia");
    }

}
