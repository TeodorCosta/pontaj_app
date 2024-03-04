package com.ECPI.pontaj_application.service;

import com.ECPI.pontaj_application.entity.Angajat;
import com.ECPI.pontaj_application.entity.TimpProiect;
import com.ECPI.pontaj_application.repository.AngajatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AngajatService {
    @Autowired
    AngajatRepository angajatRepository;

    public void saveAngajat(Angajat angajat){

        angajatRepository.save(angajat);
    }

    public List<Angajat> getAngajati(){
        return angajatRepository.findAll();
    }
    public void deleteAngajat(UUID id){
        angajatRepository.deleteById(id);
    }

    public Angajat getAngajatById(UUID id){
        return angajatRepository.getReferenceById(id);

    }

    public List<Angajat> findAngajat(String searchItem1,String searchItem2,String searchItem3) {
        return angajatRepository.findByNumeContainingIgnoreCaseOrPrenumeContainingIgnoreCaseOrRolContainingIgnoreCase(searchItem1, searchItem2, searchItem3);
    }
    public void addTimpProiectToAngajat(UUID id, TimpProiect timpProiect){
        Angajat angajat = angajatRepository.getReferenceById(id);
        angajat.addToProiect(timpProiect);
        angajatRepository.save(angajat);
    }

}
