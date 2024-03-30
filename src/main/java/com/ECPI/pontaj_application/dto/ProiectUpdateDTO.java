package com.ECPI.pontaj_application.dto;
import com.ECPI.pontaj_application.entity.TimpProiect;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProiectUpdateDTO {
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



    private String dataCODE;


    private String dataLivrare;



    private String dataFactura;

    private String nrFactura;


    public LocalDate stringToLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(dateString, formatter);

    }
}
