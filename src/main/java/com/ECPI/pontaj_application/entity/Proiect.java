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
    private String client;


    @OneToMany(mappedBy = "proiect")
    private List<TimpProiect> angajati;

    private String nrComandaInt;
    private String nrComandaClient;
    private Float valoareFactura;
    private Float valoareFacturata;
    private Float greutate;
    private String transportator;
    private String adresaLivrare;


    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDate dataCODE;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDate dataLivrare;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDate dataPlecare;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDate dataFactura;

    private  String nrFactura;




    public String getYearAndMonthAndDayDataFactura(){
        return String.valueOf(this.dataFactura.getYear()) + "-" + String.valueOf(this.dataFactura.getMonth()) + "-" + String.valueOf(this.dataFactura.getDayOfMonth());
    }

    public String getYearAndMonthAndDayDataPlecare(){
        return String.valueOf(this.dataPlecare.getYear()) + "-" + String.valueOf(this.dataPlecare.getMonth()) + "-" + String.valueOf(this.dataPlecare.getDayOfMonth());
    }
    public String getYearAndMonthAndDayDataLivrare(){
        return String.valueOf(this.dataLivrare.getYear()) + "-" + String.valueOf(this.dataLivrare.getMonth()) + "-" + String.valueOf(this.dataLivrare.getDayOfMonth());
    }

    public String getYearAndMonthAndDayDataCODE(){
        return String.valueOf(this.dataCODE.getYear()) + "-" + String.valueOf(this.dataCODE.getMonth()) + "-" + String.valueOf(this.dataCODE.getDayOfMonth());
    }
}
