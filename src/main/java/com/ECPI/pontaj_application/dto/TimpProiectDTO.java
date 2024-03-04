package com.ECPI.pontaj_application.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TimpProiectDTO {

    private String proiect_id;
    private float ore;

    private LocalDate data;

    private String angajat_id;
}
