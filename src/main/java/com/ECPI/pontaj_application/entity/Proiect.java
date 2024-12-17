package com.ECPI.pontaj_application.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    private LocalDate dataFactura;

    private String nrFactura;

    private boolean Livrat;

    public String getYearAndMonthAndDayDataFactura() {
        return this.dataFactura != null ? this.dataFactura.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) : "";
    }

    public String getYearAndMonthAndDayDataLivrare() {
        return this.dataLivrare != null ? this.dataLivrare.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) : "";
    }

    public String getYearAndMonthAndDayDataCODE() {
        return this.dataCODE != null ? this.dataCODE.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) : "";
    }

    public float getTotalHours() {
        float totalHours = 0;
        if (angajati != null) {
            for (TimpProiect timpProiect : angajati) {


                totalHours += timpProiect.getOre();

            }
        }
        return totalHours;
    }

    public float getTotalHoursForMascat() {
        float totalHours = 0;
        if (angajati != null) {
            for (TimpProiect timpProiect : angajati) {

                if (timpProiect.isWeekend() == false) {
                    if (timpProiect.isMascat() == true) {
                        totalHours += timpProiect.getOre();

                    }
                }
            }
        }
        return totalHours;
    }

    public float getTotalHoursForWeekend() {
        float totalHours = 0;
        if (angajati != null) {
            for (TimpProiect timpProiect : angajati) {
                if (timpProiect.isWeekend()) {
                    totalHours += timpProiect.getOre();
                }
            }
        }
        return totalHours;
    }
    public float getTotalHoursForSuplimentare() {
        float totalHours = 0;
        if (angajati != null) {
            for (TimpProiect timpProiect : angajati) {
                if (timpProiect.isSuplimentare()) {
                    totalHours += timpProiect.getOre();
                }
            }
        }
        return totalHours;
    }

    public float getTotalHoursForDate(LocalDate specificDate) {
        float totalHours = 0;
        if (angajati != null) {
            for (TimpProiect timpProiect : angajati) {
                if (timpProiect.getData().equals(specificDate)) {
                    totalHours += timpProiect.getOre();
                }
            }
        }
        return totalHours;
    }

    public float getTotalHoursForMonth(int month) {
        float totalHours = 0;
        if (angajati != null) {
            for (TimpProiect timpProiect : angajati) {
                if (timpProiect.getData().getMonthValue() == month) {
                    totalHours += timpProiect.getOre();
                }
            }
        }
        return totalHours;
    }
    public float calculateTarif() {
        float totalTarif = 0;

        for (TimpProiect timpProiect : angajati) {
            // Filter time entries for the specific month

                float employeeTarif = timpProiect.getAngajat().getTarif(); // Get the employee's tariff
                // Calculate tariff based on whether it's a weekend or not
                if (timpProiect.isWeekend()) {
                    totalTarif += timpProiect.getOre() * employeeTarif * 2; // Assuming weekend hours are paid double
                } else if (timpProiect.isMascat()) {
                    totalTarif += timpProiect.getOre() * employeeTarif / 2;
                } else {
                    totalTarif += timpProiect.getOre() * employeeTarif;
                }
            }

        return totalTarif;
    }

    public float calculateTarifInMonth(int month) {
        float totalTarif = 0;

        for (TimpProiect timpProiect : angajati) {
            // Filter time entries for the specific month
            if (timpProiect.getData().getMonthValue() == month) {
                float employeeTarif = timpProiect.getAngajat().getTarif(); // Get the employee's tariff
                // Calculate tariff based on whether it's a weekend or not
                if (timpProiect.isWeekend()) {
                    totalTarif += timpProiect.getOre() * employeeTarif * 2; // Assuming weekend hours are paid double
                } else if (timpProiect.isMascat()) {
                    totalTarif += timpProiect.getOre() * employeeTarif / 2;
                } else if(timpProiect.isSuplimentare()){
                     totalTarif +=timpProiect.getOre()*employeeTarif*1.75f;
                }
                else {
                    totalTarif += timpProiect.getOre() * employeeTarif;
                }
            }
        }
            return totalTarif;
        }


        public float calculateTarifInDate (LocalDate specificDate){
            float totalTarif = 0;

            for (TimpProiect timpProiect : angajati) {
                // Filter time entries for the specific date
                if (timpProiect.getData().equals(specificDate)) {
                    float employeeTarif = timpProiect.getAngajat().getTarif(); // Get the employee's tariff
                    // Calculate tariff based on whether it's a weekend or not
                    if (timpProiect.isWeekend()) {
                        totalTarif += timpProiect.getOre() * employeeTarif * 2; // Assuming weekend hours are paid double
                    } else if (timpProiect.isMascat()) {
                        totalTarif += timpProiect.getOre() * employeeTarif / 2;
                    } else if(timpProiect.isSuplimentare()){
                        totalTarif +=timpProiect.getOre()*employeeTarif*1.75f;
                    }
                    else {
                        totalTarif += timpProiect.getOre() * employeeTarif;
                    }
                }
            }

            return totalTarif;
        }
    public long getDaysUntilDelivery() {
        LocalDate today = LocalDate.now();
        long daysUntilDelivery = ChronoUnit.DAYS.between(today, this.dataLivrare);
        return daysUntilDelivery;
    }
    public boolean areAllDatesSame() {
        LocalDate firstDate = this.dataCODE != null ? this.dataCODE : this.dataLivrare != null ? this.dataLivrare : this.dataFactura;
        return this.dataCODE != null && this.dataLivrare != null && this.dataFactura != null &&
                this.dataCODE.equals(firstDate) && this.dataLivrare.equals(firstDate) && this.dataFactura.equals(firstDate);
    }

}

