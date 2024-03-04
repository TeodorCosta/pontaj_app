package com.ECPI.pontaj_application.service;

import com.ECPI.pontaj_application.entity.TimpProiect;
import com.ECPI.pontaj_application.repository.TimpProiectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TimpProiectService {
    @Autowired
    TimpProiectRepository timpProiectRepository;

    public void saveTimpProiect(TimpProiect timpProiect){
          timpProiectRepository.save(timpProiect);
    }

    public float calculateSumOfOreForAngajat(UUID angajatId) {
        List<TimpProiect> timpProiectList = timpProiectRepository.findByAngajatId(angajatId);
        float sum = 0;

        if (timpProiectList != null) {
            for (TimpProiect timpProiect : timpProiectList) {
                sum += timpProiect.getOre();
            }
        }

        return sum;
    }

    public List<TimpProiect> findAllByMonth(int month) {
        return timpProiectRepository.findAllByMonth(month);
    }

    public List<TimpProiect> findAllByMonthAndAngajatId(int month, UUID angajatId) {
        return timpProiectRepository.findAllByMonthAndAngajatId(month, angajatId);
    }
}
