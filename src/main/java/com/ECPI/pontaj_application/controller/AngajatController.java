package com.ECPI.pontaj_application.controller;

import com.ECPI.pontaj_application.dto.TimpProiectDTO;
import com.ECPI.pontaj_application.entity.Angajat;
import com.ECPI.pontaj_application.entity.TimpProiect;
import com.ECPI.pontaj_application.mapper.ProiectMapper;
import com.ECPI.pontaj_application.service.AngajatService;
import com.ECPI.pontaj_application.service.ProiectService;
import com.ECPI.pontaj_application.service.TimpProiectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class AngajatController {
    @Autowired
    private AngajatService angajatService;

    @Autowired
    private ProiectService proiectService;

    @Autowired
    private TimpProiectService timpProiectService;

    @Autowired
    private ProiectMapper proiectMapper;


    @GetMapping("/angajati")
    public String displayAngajati(Model model) {
        List<Angajat> angajati = angajatService.getAngajati();
        model.addAttribute("angajati", angajati);
        model.addAttribute("searchItem", new String());

        return ("angajati");
    }

    @GetMapping("/angajat_form")
    public String home(Model model) {
        model.addAttribute("angajat", new Angajat());
        return "angajat-form";
    }

    @PostMapping("/save_angajat")
    public String saveAngajat(Angajat angajat) {
        angajatService.saveAngajat(angajat);

        return ("redirect:/angajati");
    }

    @PostMapping("/search")
    public String angajatSearch(Model model, @RequestParam("searchItem") String searchItem) {
        List<Angajat> searchResults = angajatService.findAngajat(searchItem, searchItem, searchItem);
        model.addAttribute("results", searchResults);
        model.addAttribute("query", searchItem);
        return "results-angajat";
    }

    @GetMapping("/delete/{id}")
    public String deleteAngajat(Model model, @PathVariable UUID id) {
        angajatService.deleteAngajat(id);
        return "redirect:/angajati";
    }

    @GetMapping("/updateAngajatForm/{id}")
    public String updateAngajatForm(@PathVariable UUID id, Model model) {
        model.addAttribute("angajat", angajatService.getAngajatById(id));
        return "angajat-update-form";
    }

    @GetMapping("/vizualizare/{id}")
    public String vizualizare(Model model, @PathVariable UUID id, @ModelAttribute("timpProiect2") TimpProiectDTO timpProiect) {
        Angajat angajat = angajatService.getAngajatById(id);
        System.out.println(angajat.toString());
        model.addAttribute("angajat", angajat);
        model.addAttribute("proiecte", proiectService.getProiecte());
        model.addAttribute("utils", proiectService);
        model.addAttribute("ore_sum", timpProiectService.calculateSumOfOreForAngajat(id));
        return "vizualizare";
    }

    @PostMapping("searchVizualizare/{id}")
    public String searchVizualizare(Model model, @PathVariable UUID id, @ModelAttribute("timpProiect2") TimpProiectDTO timpProiectDTO, int searchItem) {
        List<TimpProiect> searchResults = timpProiectService.findAllByMonthAndAngajatId(searchItem, id);
        Angajat angajat = angajatService.getAngajatById(id);
        System.out.println(angajat.toString());
        model.addAttribute("angajat", angajat);
        model.addAttribute("proiecte", proiectService.getProiecte());
        model.addAttribute("utils", proiectService);
        model.addAttribute("results", searchResults);
        model.addAttribute("query", searchItem);
        model.addAttribute("ore_sum", timpProiectService.calculateSumOfOreForAngajat(id));
        return "results-vizualizare";
    }
}
