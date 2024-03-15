package com.ECPI.pontaj_application.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
@ToString
public class Angajat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nume;
    private String prenume;
    private String rol;

    @OneToMany(mappedBy = "angajat", cascade = CascadeType.ALL)
    private List<TimpProiect> proiecte;

    private boolean activ;
    //test

    public void addToProiect(TimpProiect timpProiect){
        this.proiecte.add(timpProiect);
    }
    public void removeProiect(TimpProiect timpProiect){
        this.proiecte.remove(timpProiect);
    }

    public String fullName(){
        return this.nume + " " + this.prenume;
    }

    public float calculateHoursOnProiect(Proiect proiect) {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            if (!timpProiect.getProiect().isLivrat() && timpProiect.getProiect().equals(proiect)) {
                totalHours += timpProiect.getOre();
            }
        }

        return totalHours;
    }

    public float calculateHoursOnProiectInMonth(Proiect proiect, int month) {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            if (!timpProiect.getProiect().isLivrat() && timpProiect.getProiect().equals(proiect) && timpProiect.getData().getMonthValue() == month) {
                totalHours += timpProiect.getOre();
            }
        }

        return totalHours;
    }

    public float calculateHoursOnProiectOnDate(Proiect proiect, LocalDate date) {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            if (!timpProiect.getProiect().isLivrat() && timpProiect.getProiect().equals(proiect) && timpProiect.getData().equals(date)) {
                totalHours += timpProiect.getOre();
            }
        }

        return totalHours;
    }

    public float calculateTotalHours() {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            if(!timpProiect.getProiect().isLivrat()) {
                totalHours += timpProiect.getOre();
            }
        }

        return totalHours;
    }

    public float calculateTotalHoursOnDate(LocalDate date) {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            if (!timpProiect.getProiect().isLivrat() && timpProiect.getData().equals(date)) { // Assuming "getData()" returns the LocalDate object
                totalHours += timpProiect.getOre();
            }
        }

        return totalHours;
    }


    public float calculateTotalHoursInMonth(int month) {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            // Filter projects where Livrat is false
            if (!timpProiect.getProiect().isLivrat() && timpProiect.getData().getMonthValue() == month) {
                totalHours += timpProiect.getOre();
            }
        }

        return totalHours;
    }





}
