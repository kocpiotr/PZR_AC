package com.pzr.adminconsole.controllers;

import com.pzr.adminconsole.entities.Orderr;
import com.pzr.adminconsole.entities.enums.TimeOfDayEnum;
import com.pzr.adminconsole.repositories.CityRepository;
import com.pzr.adminconsole.repositories.DistrictRepository;
import com.pzr.adminconsole.repositories.OrderRepository;
import com.pzr.adminconsole.repositories.SpecializationRepository;
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

    public OrderController(OrderRepository orderRepository, SpecializationRepository specializationRepository, CityRepository cityRepository, DistrictRepository districtRepository) {
        this.orderRepository = orderRepository;
        this.specializationRepository = specializationRepository;
        this.cityRepository = cityRepository;
        this.districtRepository = districtRepository;
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
        orderRepository.save(orderr);

        return new ModelAndView("redirect:/zlecenia");
    }

}
