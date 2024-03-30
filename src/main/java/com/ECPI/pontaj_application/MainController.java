package com.ECPI.pontaj_application;


import com.ECPI.pontaj_application.entity.Angajat;
import com.ECPI.pontaj_application.entity.Proiect;
import com.ECPI.pontaj_application.entity.TimpProiect;
import com.ECPI.pontaj_application.mapper.ProiectMapper;
import com.ECPI.pontaj_application.service.AngajatService;
import com.ECPI.pontaj_application.service.ProiectService;
import com.ECPI.pontaj_application.service.TimpProiectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class MainController {
    @Autowired
    private AngajatService angajatService;

    @Autowired
    private ProiectService proiectService;

    @Autowired
    private TimpProiectService timpProiectService;

    @Autowired
    private ProiectMapper proiectMapper;


    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }


    @GetMapping("/home")
    public String home() {
        return ("home");
    }




    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/raport")
    public String displayAngajatiAndProiecte(Model model) {
        List<TimpProiect> timpProiect = timpProiectService.getTimpProiecte();
        List<Angajat> angajati = angajatService.getAngajati().stream()
                .filter(angajat -> !angajat.isActiv())
                .collect(Collectors.toList());;
        List<Proiect> proiecte = proiectService.getProiecte().stream()
                .filter(proiect -> !proiect.isLivrat())// Assuming the getter method is isLivrat()
                .collect(Collectors.toList());;
        model.addAttribute("angajati", angajati);
        model.addAttribute("proiecte", proiecte);
        model.addAttribute("timpProiect", timpProiect);
        return "raport";
    }

    @PostMapping("/raport_search_month")
    public  String displayAngajatiAndProiecteResultsMonth(Model model, Integer searchItem){
        List<TimpProiect> timpProiect =timpProiectService.findAllByMonth(searchItem);
        List<Angajat> angajati =angajatService.getAngajati().stream()
                .filter(angajat -> !angajat.isActiv())
                .collect(Collectors.toList());;
        List<Proiect> proiecte =proiectService.getProiecte().stream()
                .filter(proiect -> !proiect.isLivrat())// Assuming the getter method is isLivrat()
                .collect(Collectors.toList());;
        model.addAttribute("angajati", angajati);
        model.addAttribute("proiecte",proiecte);
        model.addAttribute("timpProiect",timpProiect);
        model.addAttribute("query",searchItem);
        model.addAttribute("Month",searchItem);
        return("raport-search");
    }
    @PostMapping("/raport_search_date")
    public  String displayAngajatiAndProiecteResultsMonth(Model model, LocalDate searchItem){
        List<TimpProiect> timpProiect =timpProiectService.findAllByDate(searchItem);
        List<Angajat> angajati =angajatService.getAngajati();
        List<Proiect> proiecte =proiectService.getProiecte().stream()
                .filter(proiect -> !proiect.isLivrat())// Assuming the getter method is isLivrat()
                .collect(Collectors.toList());;
        model.addAttribute("angajati", angajati);
        model.addAttribute("proiecte",proiecte);
        model.addAttribute("timpProiect",timpProiect);
        model.addAttribute("query",searchItem);
        model.addAttribute("date",searchItem);
        return("raport-search-date");
    }



}
