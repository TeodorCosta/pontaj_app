package com.ECPI.pontaj_application.entity;

import jakarta.persistence.*;
import lombok.*;

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
            if (timpProiect.getProiect().equals(proiect)) {
                totalHours += timpProiect.getOre();
            }
        }

        return totalHours;
    }
    public float calculateTotalHours() {
        float totalHours = 0;

        for (TimpProiect timpProiect : proiecte) {
            totalHours += timpProiect.getOre();
        }

        return totalHours;
    }



}
