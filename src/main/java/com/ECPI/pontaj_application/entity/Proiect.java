package com.ECPI.pontaj_application.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
public class Proiect {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nume;
    private String client;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDate data;


    @OneToMany(mappedBy = "proiect")
    private List<TimpProiect> angajati;

    private String nrComanda;


    public String getYearAndMonthAndDay(){
        return String.valueOf(this.data.getYear()) + "-" + String.valueOf(this.data.getMonth()) + "-" + String.valueOf(this.data.getDayOfMonth());
    }
}
