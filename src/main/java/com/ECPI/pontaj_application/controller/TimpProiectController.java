package com.ECPI.pontaj_application.controller;

import com.ECPI.pontaj_application.dto.TimpProiectDTO;
import com.ECPI.pontaj_application.entity.Angajat;
import com.ECPI.pontaj_application.entity.Proiect;
import com.ECPI.pontaj_application.entity.TimpProiect;
import com.ECPI.pontaj_application.mapper.ProiectMapper;
import com.ECPI.pontaj_application.service.AngajatService;
import com.ECPI.pontaj_application.service.ProiectService;
import com.ECPI.pontaj_application.service.TimpProiectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class TimpProiectController {
    @Autowired
    private AngajatService angajatService;

    @Autowired
    private ProiectService proiectService;

    @Autowired
    private TimpProiectService timpProiectService;

    @Autowired
    private ProiectMapper proiectMapper;


    @PostMapping("/save_timp_proiect")
    public String saveTimpProiect(TimpProiect timpProiect) {
        timpProiectService.saveTimpProiect(timpProiect);
        return ("redirect:/timp_proiect_form");
    }

    @GetMapping("deleteTimpProiectAngajat/{id}")
    public String deleteTimpProiect(Model model, @PathVariable Integer id, UUID id2){
        Angajat angajat = timpProiectService.getAngajatByTimpProiectId(id);
        timpProiectService.deleteTimpProiect(timpProiectService.getTimpProiectById(id));
        id2 = angajat.getId();
        model.addAttribute("id2", id2);
        return "redirect:/vizualizare/" + id2;
    }
    @GetMapping("deleteTimpProiectProiect/{id}")
    public String deleteTimpProiectProiect(Model model, @PathVariable Integer id, UUID id2){
        Proiect proiect = timpProiectService.getProiectByTimpProiectId(id);
        timpProiectService.deleteTimpProiect(timpProiectService.getTimpProiectById(id));
        id2 = proiect.getId();
        model.addAttribute("id2", id2);
        return "redirect:/vizualizareProiect/" + id2;
    }

    @GetMapping("/adauga-angajat-pe-proiect")
    public String vizualizare(Model model,@ModelAttribute("timpProiect2") TimpProiectDTO timpProiect){

        if(!(timpProiect.getProiect_id() == null || timpProiect.getAngajat_id() == null)){

            Angajat selectedAngajat = angajatService.getAngajatById(UUID.fromString(timpProiect.getAngajat_id()));
            Proiect selectedProiect = proiectService.getProiectById(UUID.fromString(timpProiect.getProiect_id()));

            model.addAttribute("selectedAngajat", selectedAngajat);
            model.addAttribute("selectedProiect", selectedProiect);
            model.addAttribute("currentAngajat", true);
            model.addAttribute("currentProiect", true);
        }
        else{
            model.addAttribute("currentAngajat", false);
            model.addAttribute("currentProiect", false);
        }
        List<Proiect> proiecte =proiectService.getProiecte().stream()
                .filter(proiect -> !proiect.isLivrat())// Assuming the getter method is isLivrat()
                .collect(Collectors.toList());

        model.addAttribute("angajati", angajatService.getAngajati().stream()
                .filter(angajat -> !angajat.isActiv())
                .collect(Collectors.toList()));

        model.addAttribute("proiecte",proiecte);
        model.addAttribute("utils", proiectService);

        return "adauga-angajat-pe-proiect";
    }

    @PostMapping("saveTimpProiect")
    public String saveOreProiectPtAngajat(TimpProiectDTO timpProiect,  RedirectAttributes redirectAttributes){
        Angajat angajat = angajatService.getAngajatById(UUID.fromString(timpProiect.getAngajat_id()));
        TimpProiect timpProiectToBeAdded= TimpProiect.builder()
                .angajat(angajat)
                .proiect(proiectService.getProiectById(UUID.fromString(timpProiect.getProiect_id())))
                .ore(timpProiect.getOre())
                .data(timpProiect.getData())
                .build();

        timpProiectService.saveTimpProiect(timpProiectToBeAdded);
        angajat.addToProiect(timpProiectToBeAdded);
        System.out.println(timpProiect.toString());
        redirectAttributes.addFlashAttribute("timpProiect2", timpProiect);
        return "redirect:/adauga-angajat-pe-proiect";
    }

    @GetMapping("/exportToExcel")
    public ResponseEntity<byte[]> exportTimpProiectEntitiesToExcel() {
        try {
            byte[] excelBytes = timpProiectService.exportTimpProiectEntitiesToExcel();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", "Pontaj.xlsx");
            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
