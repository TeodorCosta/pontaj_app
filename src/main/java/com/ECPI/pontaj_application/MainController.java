package com.ECPI.pontaj_application;

import com.ECPI.pontaj_application.dto.TimpProiectDTO;
import com.ECPI.pontaj_application.entity.Angajat;
import com.ECPI.pontaj_application.entity.Proiect;
import com.ECPI.pontaj_application.entity.TimpProiect;
import com.ECPI.pontaj_application.repository.AngajatRepository;
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

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/angajat_form")
    public String home(Model model) {
        model.addAttribute("angajat", new Angajat());
        return "angajat-form";
    }

    @GetMapping("/home")
    public String home() {
        return ("home");
    }

    @PostMapping("/save_angajat")
    public String saveAngajat(Angajat angajat) {
        angajatService.saveAngajat(angajat);

        return ("redirect:/angajati");
    }

    @PostMapping("/save_proiect")
    public String saveProiect(Proiect proiect) {
        proiectService.saveProiect(proiect);
        return ("redirect:/proiecte");
    }

    @GetMapping("/save_proiect_form")
    public String proiectForm(Model model) {
        model.addAttribute("proiect", new Proiect());
        return "proiect-form";

    }

    @PostMapping("/save_timp_proiect")
    public String saveTimpProiect(TimpProiect timpProiect) {
        timpProiectService.saveTimpProiect(timpProiect);
        return ("redirect:/timp_proiect_form");
    }
//    @PostMapping("/search")
//    public String angajatSearch( Model model, @RequestParam("searchItem") String searchItem) {
//        List<Angajat> searchResults = angajatRepository.findByNume(searchItem);
//        System.out.println(searchItem);
//        model.addAttribute("results", searchResults);
//        model.addAttribute("query", searchItem);
//        return "results-angajat";
//    }
//    @PostMapping("/add_timeonproject/{id}")
//    public String addTimeProject(Model model, @PathVariable UUID id){
//
//
//    }

    @PostMapping("/search")
    public String angajatSearch(Model model, @RequestParam("searchItem") String searchItem) {
        List<Angajat> searchResults = angajatService.findAngajat(searchItem, searchItem, searchItem);
        model.addAttribute("results", searchResults);
        model.addAttribute("query", searchItem);
        return "results-angajat";
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


    @GetMapping("/angajati")
    public String displayAngajati(Model model) {
        List<Angajat> angajati = angajatService.getAngajati();
        model.addAttribute("angajati", angajati);
        model.addAttribute("searchItem", new String());

        return ("angajati");
    }

    @GetMapping("/proiecte")
    public String displayProiecte(Model model) {
        List<Proiect> proiecte = proiectService.getProiecte();
        model.addAttribute("proiecte", proiecte);

        return ("proiecte");
    }

    @GetMapping("/delete/{id}")
    public String deleteAngajat(Model model, @PathVariable UUID id) {
        angajatService.deleteAngajat(id);
        return "redirect:/angajati";
    }

    @GetMapping("/deleteProiect/{id}")
    public String deleteProiect(Model model, @PathVariable UUID id) {
        proiectService.deleteProiect(id);
        return "redirect:/proiecte";
    }

    @GetMapping("/updateAngajatForm/{id}")
    public String updateAngajatForm(@PathVariable UUID id, Model model) {
        model.addAttribute("angajat", angajatService.getAngajatById(id));
        return "angajat-update-form";
    }

    @GetMapping("/updateProiectForm/{id}")
    public String updateProiectForm(@PathVariable UUID id, Model model) {
        model.addAttribute("proiect", proiectService.getProiectById(id));
        return "proiect-update-form";
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

    @GetMapping("/detaliProiect/{id}")
    public String detaliProiect(Model model, @PathVariable UUID id){

       Proiect proiect = proiectService.getProiectById(id);
       model.addAttribute("proiect",proiect);
        return"detali-proiect";
}

   @GetMapping("deleteTimpProiect/{id}")
   public String deleteTimpProiect(Model model, @PathVariable Integer id){
        timpProiectService.deleteTimpProiect(timpProiectService.getTimpProiectById(id));
        return "vizualizareProiect";

    }
//    @GetMapping("/test")
//    public String test(){
//        Angajat angajat = angajatService.getAngajati().get(0);
//        TimpProiect timpProiect= TimpProiect.builder()
//                .ore(3)
//                .data(LocalDate.of(2023,12,2))
//                .proiect(proiectService.getProiecte().get(0))
//                .angajat(angajat)
//                .build();
//        timpProiectService.saveTimpProiect(timpProiect);
//
//        angajat.addToProiect(timpProiect);
//        return"angajati";
//    }

//    @PostMapping("/save/{angajat_id}/{proiect_id}")
//    public String saveProjectHoursForEmployee(@PathVariable UUID angajat_id,@PathVariable UUID proiect_id, @ModelAttribute("timpProiect") TimpProiect timpProiect){
//
//        Angajat angajat = angajatService.getAngajatById(angajat_id);
//        Proiect proiect = proiectService.getProiectById(proiect_id);
//        TimpProiect timpProiectToBeAdded = TimpProiect.builder()
//                .angajat(angajat)
//                .proiect(proiect)
//                .ore(timpProiect.getOre())
//                .data(timpProiect.getData())
//                .build();
//
//        timpProiectService.saveTimpProiect(timpProiectToBeAdded);
//        angajat.addToProiect(timpProiectToBeAdded);
//
//        return "redirect:vizualizare/" + angajat.getId() ;
//    }


    @GetMapping("/adauga-angajat-pe-proiect")
    public String vizualizare(Model model,@ModelAttribute("timpProiect2") TimpProiectDTO timpProiect){
        model.addAttribute("angajati", angajatService.getAngajati());
        model.addAttribute("proiecte",proiectService.getProiecte());
        model.addAttribute("utils", proiectService);

        return "adauga-angajat-pe-proiect";
    }

    @PostMapping("saveTimpProiect")
    public String saveOreProiectPtAngajat(TimpProiectDTO timpProiect){
        Angajat angajat = angajatService.getAngajatById(UUID.fromString(timpProiect.getAngajat_id()));
        TimpProiect timpProiectToBeAdded= TimpProiect.builder()
                .angajat(angajat)
                .proiect(proiectService.getProiectById(UUID.fromString(timpProiect.getProiect_id())))
                .ore(timpProiect.getOre())
                .data(timpProiect.getData())
                .build();

        timpProiectService.saveTimpProiect(timpProiectToBeAdded);
        angajat.addToProiect(timpProiectToBeAdded);

        return "redirect:/adauga-angajat-pe-proiect";
    }


//    @PostMapping("/save/{angajat_id}")
//    public String saveProjectHoursForEmployee(@PathVariable UUID angajat_id, TimpProiectDTO timpProiect){
//
//        Angajat angajat = angajatService.getAngajatById(angajat_id);
//        TimpProiect timpProiectToBeAdded = TimpProiect.builder()
//                .angajat(angajat)
//                .proiect(proiectService.getProiectById(UUID.fromString(timpProiect.getProiect_id())))
//                .ore(timpProiect.getOre())
//                .data(timpProiect.getData())
//                .build();
//
//        timpProiectService.saveTimpProiect(timpProiectToBeAdded);
//        angajat.addToProiect(timpProiectToBeAdded);
//
//        return "redirect:vizualizare/" + angajat.getId() ;
//    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }
}
