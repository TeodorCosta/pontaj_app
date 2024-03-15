package com.ECPI.pontaj_application;


import com.ECPI.pontaj_application.entity.Angajat;
import com.ECPI.pontaj_application.entity.Proiect;
import com.ECPI.pontaj_application.entity.TimpProiect;
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


    /*
    @PostMapping("/search")
    public String angajatSearch( Model model, @RequestParam("searchItem") String searchItem) {
        List<Angajat> searchResults = angajatRepository.findByNume(searchItem);
        System.out.println(searchItem);
        model.addAttribute("results", searchResults);
        model.addAttribute("query", searchItem);
        return "results-angajat";
    }
    @PostMapping("/add_timeonproject/{id}")
    public String addTimeProject(Model model, @PathVariable UUID id){
}
*/
    /*
    @GetMapping("/test")
    public String test(){
        Angajat angajat = angajatService.getAngajati().get(0);
        TimpProiect timpProiect= TimpProiect.builder()
                .ore(3)
                .data(LocalDate.of(2023,12,2))
                .proiect(proiectService.getProiecte().get(0))
                .angajat(angajat)
                .build();
        timpProiectService.saveTimpProiect(timpProiect);

        angajat.addToProiect(timpProiect);
        return"angajati";
    }

    @PostMapping("/save/{angajat_id}/{proiect_id}")
    public String saveProjectHoursForEmployee(@PathVariable UUID angajat_id,@PathVariable UUID proiect_id, @ModelAttribute("timpProiect") TimpProiect timpProiect){

        Angajat angajat = angajatService.getAngajatById(angajat_id);
        Proiect proiect = proiectService.getProiectById(proiect_id);
        TimpProiect timpProiectToBeAdded = TimpProiect.builder()
                .angajat(angajat)
                .proiect(proiect)
                .ore(timpProiect.getOre())
                .data(timpProiect.getData())
                .build();

        timpProiectService.saveTimpProiect(timpProiectToBeAdded);
        angajat.addToProiect(timpProiectToBeAdded);

        return "redirect:vizualizare/" + angajat.getId() ;
    }
*/
    /*
    @PostMapping("/save/{angajat_id}")
    public String saveProjectHoursForEmployee(@PathVariable UUID angajat_id, TimpProiectDTO timpProiect){

        Angajat angajat = angajatService.getAngajatById(angajat_id);
        TimpProiect timpProiectToBeAdded = TimpProiect.builder()
                .angajat(angajat)
                .proiect(proiectService.getProiectById(UUID.fromString(timpProiect.getProiect_id())))
                .ore(timpProiect.getOre())
                .data(timpProiect.getData())
                .build();

        timpProiectService.saveTimpProiect(timpProiectToBeAdded);
        angajat.addToProiect(timpProiectToBeAdded);

        return "redirect:vizualizare/" + angajat.getId() ;
    }*/

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/raport")
    public String displayAngajatiAndProiecte(Model model) {
        List<TimpProiect> timpProiect = timpProiectService.getTimpProiecte();
        List<Angajat> angajati = angajatService.getAngajati();
        List<Proiect> proiecte = proiectService.getProiecte();
        model.addAttribute("angajati", angajati);
        model.addAttribute("proiecte", proiecte);
        model.addAttribute("timpProiect", timpProiect);
        return "raport";
    }
}
