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

    private Float tarif;

    public void addToProiect(TimpProiect timpProiect) {
        this.proiecte.add(timpProiect);
    }

    public void removeProiect(TimpProiect timpProiect) {
        this.proiecte.remove(timpProiect);
    }

    public String fullName() {
        return this.nume + " " + this.prenume;
    }

    public float calculateHoursOnProiect(Proiect proiect) {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            if (!timpProiect.getProiect().isLivrat() && timpProiect.getProiect().equals(proiect)) {
                if(timpProiect.isSuplimentare()==false) {
                    if (timpProiect.isWeekend() == false) {
                        if (timpProiect.isMascat() == false) {
                            totalHours += timpProiect.getOre();
                        }
                    }
                }
            }
        }

        return totalHours;
    }

    public float calculateHoursOnProiectForMascat(Proiect proiect) {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            if (!timpProiect.getProiect().isLivrat() && timpProiect.getProiect().equals(proiect)) {
                if (timpProiect.isWeekend() == false) {
                    if (timpProiect.isMascat() == true) {
                        totalHours += timpProiect.getOre();
                    }
                }
            }
        }

        return totalHours;
    }
    public float calculateHoursOnProiectForWeekend(Proiect proiect) {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            if (!timpProiect.getProiect().isLivrat() && timpProiect.getProiect().equals(proiect)) {

                    if (timpProiect.isWeekend()) {
                        totalHours += timpProiect.getOre();
                    }

            }
        }

        return totalHours;
    }
    public float calculateHoursOnProiectForSuplimentare(Proiect proiect) {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            if (!timpProiect.getProiect().isLivrat() && timpProiect.getProiect().equals(proiect)) {

                if (timpProiect.isSuplimentare()) {
                    totalHours += timpProiect.getOre();
                }

            }
        }

        return totalHours;
    }


    public float calculateHoursOnProiectInMonth(Proiect proiect, int month) {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            if (!timpProiect.getProiect().isLivrat() && timpProiect.getProiect().equals(proiect) && timpProiect.getData().getMonthValue() == month) {
                if (timpProiect.isWeekend() == false) {
                    if (timpProiect.isMascat() == false) {
                        totalHours += timpProiect.getOre();
                    }
                }
            }
        }

        return totalHours;
    }

    public float calculateHoursOnProiectInMonthForMascat(Proiect proiect, int month) {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            if (!timpProiect.getProiect().isLivrat() && timpProiect.getProiect().equals(proiect) && timpProiect.getData().getMonthValue() == month) {
                if (timpProiect.isWeekend() == false) {
                    if (timpProiect.isMascat() == true) {
                        totalHours += timpProiect.getOre();
                    }
                }
            }
        }

        return totalHours;
    }
    public float calculateHoursOnProiectInMonthForWeekend(Proiect proiect, int month) {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            if (!timpProiect.getProiect().isLivrat() && timpProiect.getProiect().equals(proiect) && timpProiect.getData().getMonthValue() == month) {

                    if (timpProiect.isWeekend()) {
                        totalHours += timpProiect.getOre();
                    }
                }

        }

        return totalHours;
    }
    public float calculateHoursOnProiectInMonthForSuplimentare(Proiect proiect, int month) {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            if (!timpProiect.getProiect().isLivrat() && timpProiect.getProiect().equals(proiect) && timpProiect.getData().getMonthValue() == month) {

                    if (timpProiect.isSuplimentare()) {
                        totalHours += timpProiect.getOre();
                    }
                }
            }


        return totalHours;
    }


    public float calculateHoursOnProiectOnDate(Proiect proiect, LocalDate date) {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            if (!timpProiect.getProiect().isLivrat() && timpProiect.getProiect().equals(proiect) && timpProiect.getData().equals(date)) {
                if(timpProiect.isSuplimentare()==false) {
                    if (timpProiect.isWeekend() == false) {
                        if (timpProiect.isMascat() == false) {
                            totalHours += timpProiect.getOre();
                        }
                    }
                }

            }
        }

        return totalHours;
    }

    public float calculateHoursOnProiectOnDateForMascat(Proiect proiect, LocalDate date) {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            if (!timpProiect.getProiect().isLivrat() && timpProiect.getProiect().equals(proiect) && timpProiect.getData().equals(date)) {
                if(timpProiect.isSuplimentare()==false) {
                    if (timpProiect.isWeekend() == false) {
                        if (timpProiect.isMascat() == true) {
                            totalHours += timpProiect.getOre();
                        }
                    }
                }

            }
        }

        return totalHours;
    }
    public float calculateHoursOnProiectOnDateForWeekend(Proiect proiect, LocalDate date) {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            if (!timpProiect.getProiect().isLivrat() && timpProiect.getProiect().equals(proiect) && timpProiect.getData().equals(date)) {


                        if (timpProiect.isWeekend()) {
                            totalHours += timpProiect.getOre();
                        }
                    }
                }




        return totalHours;
    }
    public float calculateHoursOnProiectOnDateForSuplimentare(Proiect proiect, LocalDate date) {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            if (!timpProiect.getProiect().isLivrat() && timpProiect.getProiect().equals(proiect) && timpProiect.getData().equals(date)) {
                if(timpProiect.isSuplimentare()) {
                            totalHours += timpProiect.getOre();


                }

            }
        }

        return totalHours;
    }

    public float calculateTotalHours() {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            if (!timpProiect.getProiect().isLivrat()) {
                if(timpProiect.isSuplimentare()==false) {
                    if (timpProiect.isWeekend() == false) {
                        if (timpProiect.isMascat() == false) {
                            totalHours += timpProiect.getOre();
                        }
                    }
                }
            }
        }

        return totalHours;
    }

    public float calculateTotalHoursForMascat() {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            if (!timpProiect.getProiect().isLivrat()) {
                if(timpProiect.isSuplimentare()==false) {
                    if (timpProiect.isWeekend() == false) {
                        if (timpProiect.isMascat() == true) {
                            totalHours += timpProiect.getOre();
                        }
                    }
                }
            }
        }

        return totalHours;
    }

    public float calculateTotalHoursForWeekend() {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            if (!timpProiect.getProiect().isLivrat()) {
                if (timpProiect.isWeekend()) {

                    totalHours += timpProiect.getOre();

                }
            }
        }

        return totalHours;
    }
    public float calculateTotalHoursForSuplimentare() {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            if (!timpProiect.getProiect().isLivrat()) {
                if (timpProiect.isSuplimentare()) {

                    totalHours += timpProiect.getOre();

                }
            }
        }

        return totalHours;
    }

    public float calculateTotalHoursOnDate(LocalDate date) {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            if (!timpProiect.getProiect().isLivrat() && timpProiect.getData().equals(date)) { // Assuming "getData()" returns the LocalDate object
                if (timpProiect.isWeekend() == false) {
                    if (timpProiect.isMascat() == false) {
                        totalHours += timpProiect.getOre();
                    }
                }

            }
        }

        return totalHours;
    }

    public float calculateTotalHoursOnDateForMascat(LocalDate date) {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            if (!timpProiect.getProiect().isLivrat() && timpProiect.getData().equals(date)) { // Assuming "getData()" returns the LocalDate object
                if (timpProiect.isWeekend() == false) {
                    if (timpProiect.isMascat() == true) {
                        totalHours += timpProiect.getOre();
                    }
                }

            }
        }

        return totalHours;
    }

    public float calculateTotalHoursOnDateForWeekend(LocalDate date) {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            if (!timpProiect.getProiect().isLivrat() && timpProiect.getData().equals(date)) { // Assuming "getData()" returns the LocalDate object
                if (timpProiect.isWeekend()) {

                    totalHours += timpProiect.getOre();

                }

            }
        }

        return totalHours;
    }
    public float calculateTotalHoursOnDateForSuplimenatare(LocalDate date) {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            if (!timpProiect.getProiect().isLivrat() && timpProiect.getData().equals(date)) { // Assuming "getData()" returns the LocalDate object
                if (timpProiect.isSuplimentare()) {

                    totalHours += timpProiect.getOre();

                }

            }
        }

        return totalHours;
    }



    public float calculateTotalHoursInMonth(int month) {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            // Filter projects where Livrat is false
            if (!timpProiect.getProiect().isLivrat() && timpProiect.getData().getMonthValue() == month) {
                if (timpProiect.isWeekend() == false) {
                    if (timpProiect.isMascat() == false) {
                        totalHours += timpProiect.getOre();

                    }
                }
            }
        }

        return totalHours;
    }

    public float calculateTotalHoursInMonthForMascat(int month) {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            // Filter projects where Livrat is false
            if (!timpProiect.getProiect().isLivrat() && timpProiect.getData().getMonthValue() == month) {
                if (timpProiect.isWeekend() == false) {
                    if (timpProiect.isMascat() == true) {
                        totalHours += timpProiect.getOre();

                    }
                }
            }
        }

        return totalHours;
    }

    public float calculateTotalHoursInMonthForWeekend(int month) {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            // Filter projects where Livrat is false
            if (!timpProiect.getProiect().isLivrat() && timpProiect.getData().getMonthValue() == month) {
                if (timpProiect.isWeekend()) {
                    if (timpProiect.isMascat() == false) {
                        totalHours += timpProiect.getOre();

                    }
                }
            }
        }

        return totalHours;
    }
    public float calculateTotalHoursInMonthForSuplimentare(int month) {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            // Filter projects where Livrat is false
            if (!timpProiect.getProiect().isLivrat() && timpProiect.getData().getMonthValue() == month) {

                            if (timpProiect.isSuplimentare()){
                        totalHours += timpProiect.getOre();

                    }
                }
            }

        return totalHours;
    }

    public float calculateTarif(float tarif) {
        float suma = 0;
        float OreWeekend = 0;
        float OreMascate = 0;
        float OreNormale = 0;
        float OreSuplimentare=0;

        for (TimpProiect timpProiect : proiecte) {
            // Filter projects where Livrat is false
            if (!timpProiect.getProiect().isLivrat()) {
                if (timpProiect.isWeekend()) {

                    OreWeekend += timpProiect.getOre();

                } else if (timpProiect.isWeekend() == false) {
                    if (timpProiect.isMascat() == false) {
                        OreNormale += timpProiect.getOre();
                    }
                    else if (timpProiect.isMascat()) {
                        OreMascate += timpProiect.getOre();
                    }
                    else if(timpProiect.isSuplimentare()){
                        OreSuplimentare +=timpProiect.getOre();
                    }
                }


            }
        }
        suma = 2 * (OreWeekend * tarif) + OreNormale * tarif + (OreMascate * tarif) / 2 +(OreSuplimentare*tarif)*1.75f;
        return suma;


    }
    public float calculateTarifInMonth(int month, float tarif) {
        float suma = 0;
        float OreWeekend = 0;
        float OreMascate = 0;
        float OreNormale = 0;
        float OreSuplimentare=0;

        for (TimpProiect timpProiect : proiecte) {
            // Filter projects where Livrat is false
            if (!timpProiect.getProiect().isLivrat() && timpProiect.getData().getMonthValue() == month) {
                if (timpProiect.isWeekend()) {

                    OreWeekend += timpProiect.getOre();

                } else if (timpProiect.isWeekend() == false) {
                    if (timpProiect.isMascat() == false) {
                        OreNormale += timpProiect.getOre();
                    }
                    else if (timpProiect.isMascat()) {
                        OreMascate += timpProiect.getOre();
                    }
                    else if(timpProiect.isSuplimentare()){
                        OreSuplimentare +=timpProiect.getOre();
                    }
                }


            }
        }
        suma = 2 * (OreWeekend * tarif) + OreNormale * tarif + (OreMascate * tarif) / 2 +(OreSuplimentare*tarif)*1.75f;
        return suma;


    }

    public float calculateTarifInDate(LocalDate date, float tarif) {
        float suma = 0;
        float OreWeekend = 0;
        float OreMascate = 0;
        float OreNormale = 0;
        float OreSuplimentare=0;

        for (TimpProiect timpProiect : proiecte) {
            // Filter projects where Livrat is false
            if (!timpProiect.getProiect().isLivrat() && timpProiect.getData().equals(date)) { // Assuming "getData()" returns the LocalDate object
                if (timpProiect.isWeekend()) {

                    OreWeekend += timpProiect.getOre();

                } else if (timpProiect.isWeekend() == false) {
                    if (timpProiect.isMascat() == false) {
                        OreNormale += timpProiect.getOre();
                    }
                    if (timpProiect.isMascat()) {
                        OreMascate += timpProiect.getOre();
                    }
                    if(timpProiect.isSuplimentare()){
                        OreSuplimentare +=timpProiect.getOre();
                    }
                }


            }
        }
        suma = 2 * (OreWeekend * tarif) + OreNormale * tarif + (OreMascate * tarif) / 2 +(OreSuplimentare*tarif)*1.75f;
        return suma;


    }


}
