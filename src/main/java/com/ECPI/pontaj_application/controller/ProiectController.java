package com.ECPI.pontaj_application.controller;

import com.ECPI.pontaj_application.dto.ProiectUpdateDTO;
import com.ECPI.pontaj_application.dto.TimpProiectDTO;
import com.ECPI.pontaj_application.entity.Angajat;
import com.ECPI.pontaj_application.entity.Proiect;
import com.ECPI.pontaj_application.mapper.ProiectMapper;
import com.ECPI.pontaj_application.service.AngajatService;
import com.ECPI.pontaj_application.service.ProiectService;
import com.ECPI.pontaj_application.service.TimpProiectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Controller
public class ProiectController {
    @Autowired
    private AngajatService angajatService;

    @Autowired
    private ProiectService proiectService;

    @Autowired
    private TimpProiectService timpProiectService;

    @Autowired
    private ProiectMapper proiectMapper;


    @GetMapping("/proiecte")
    public String displayProiecte(Model model) {
        List<Proiect> proiecte = proiectService.getProiecte();
        model.addAttribute("proiecte", proiecte);

        return ("proiecte");
    }


    @GetMapping("/save_proiect_form")
    public String proiectForm(Model model) {
        model.addAttribute("proiect", new Proiect());
        return "proiect-form";
    }

    @PostMapping("/save_proiect")
    public String saveProiect(Proiect proiect) {
        proiectService.saveProiect(proiect);
        return ("redirect:/proiecte");
    }

    @GetMapping("/updateProiectForm/{id}")
    public String updateProiectForm(@PathVariable UUID id, Model model) {
        Proiect proiect =proiectService.getProiectById(id);
        ProiectUpdateDTO proiectUpdateDTO= proiectMapper.mapToProiectUpdateDTO(proiect);
        model.addAttribute("proiect",proiectUpdateDTO );
        return "proiect-update-form";
    }

    @PostMapping("update_proiect")
    public String updateProiect(ProiectUpdateDTO proiectUpdateDTO) {
        Proiect proiect = proiectMapper.maptoProiect(proiectUpdateDTO);
        proiectService.saveProiect(proiect);
        return ("redirect:/proiecte");
    }

    @GetMapping("/deleteProiect/{id}")
    public String deleteProiect(Model model, @PathVariable UUID id) {
        proiectService.deleteProiect(id);
        return "redirect:/proiecte";
    }

    @PostMapping("/searchLuna")
    public String proiectSearchLuna(Model model, @RequestParam("searchItem") int searchItem) {
        List<Proiect> searchResults = proiectService.findProiectByMonth(searchItem);
        model.addAttribute("results", searchResults);
        model.addAttribute("query", searchItem);
        return "results-proiecte";
    }

    @PostMapping("/searchProiecte")
    public String proiectSearch(Model model, @RequestParam("searchItem") String searchItem, LocalDate searchItem2, String searchItem3) {
        List<Proiect> searchResults = proiectService.findProiect(searchItem, searchItem, searchItem2, searchItem3);
        model.addAttribute("results", searchResults);
        model.addAttribute("query", searchItem);
        return "results-proiecte";
    }



    @GetMapping("/vizualizareProiect/{id}")
    public String vizualizareProiect(Model model, @PathVariable UUID id, @ModelAttribute("timpProiect2") TimpProiectDTO timpProiect) {
        Proiect proiect = proiectService.getProiectById(id);
        System.out.println(proiect.toString());
        List<Angajat> angajati = angajatService.getAngajati();
        model.addAttribute("angajati", angajati);
        model.addAttribute("proiect", proiect);
        model.addAttribute("utils", angajatService);
        return "vizualizareProiect";
    }

    @GetMapping("/detaliProiect/{id}")
    public String detaliProiect(Model model, @PathVariable UUID id){

        Proiect proiect = proiectService.getProiectById(id);
        model.addAttribute("proiect",proiect);
        return"detali-proiect";
    }

}
