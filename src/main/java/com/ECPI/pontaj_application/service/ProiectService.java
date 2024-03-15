package com.ECPI.pontaj_application.service;

import com.ECPI.pontaj_application.entity.Angajat;
import com.ECPI.pontaj_application.entity.Proiect;
import com.ECPI.pontaj_application.repository.ProiectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ProiectService {
    @Autowired
    ProiectRepository proiectRepository;

    public void saveProiect(Proiect proiect){
        proiectRepository.save(proiect);
    }
    public List<Proiect> getProiecte(){
        return proiectRepository.findAll();
    }

    public List<Proiect> findProiect(String searchItem1, String searchItem2, LocalDate searchItem3, String searchItem4) {
        return proiectRepository.findAllByNrComandaIntContainingIgnoreCaseOrClientContainingIgnoreCaseOrDataCODEOrNrComandaClientContainingIgnoreCase(searchItem1, searchItem2, searchItem3, searchItem4);
    }

    public List<Proiect> findProiectByMonth(int month){
        return proiectRepository.findAllByMonth(month);
    }

    public void deleteProiect(UUID id){
        proiectRepository.deleteById(id);
    }
    public Proiect getProiectById(UUID id){
       return proiectRepository.getReferenceById(id);
    }





}
