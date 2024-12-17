package com.ECPI.pontaj_application.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"id"})
})
public class TimpProiect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "proiect_id")
    private Proiect proiect;
    private float ore;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "angajat_id")
    private Angajat angajat;

    private boolean mascat;



    private boolean weekend;

    private boolean suplimentare;

    public String getYearAndMonth(){
        return String.valueOf(this.data.getYear()) + "-" + String.valueOf(this.data.getMonth());
    }

    public String getYearAndMonthAndDay(){
        return String.valueOf(this.data.getYear()) + "-" + String.valueOf(this.data.getMonth()) + "-" + String.valueOf(this.data.getDayOfMonth());
    }

    public Angajat getAngajat() {
        return angajat;
    }





}

