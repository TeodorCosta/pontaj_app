package com.ECPI.pontaj_application.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import java.time.format.DateTimeFormatter;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataCODE;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataLivrare;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataPlecare;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataFactura;

    private  String nrFactura;






    public String getYearAndMonthAndDayDataFactura() {
        return this.dataFactura != null ? this.dataFactura.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) : "";
    }

    public String getYearAndMonthAndDayDataPlecare() {
        return this.dataPlecare != null ? this.dataPlecare.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) : "";
    }

    public String getYearAndMonthAndDayDataLivrare() {
        return this.dataLivrare != null ? this.dataLivrare.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) : "";
    }

    public String getYearAndMonthAndDayDataCODE() {
        return this.dataCODE != null ? this.dataCODE.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) : "";
    }


}
