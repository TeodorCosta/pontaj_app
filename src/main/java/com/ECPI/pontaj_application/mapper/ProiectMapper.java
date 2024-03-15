package com.ECPI.pontaj_application.mapper;

import com.ECPI.pontaj_application.dto.ProiectUpdateDTO;
import com.ECPI.pontaj_application.entity.Proiect;
import org.springframework.stereotype.Component;

@Component
public class ProiectMapper {

    public ProiectUpdateDTO mapToProiectUpdateDTO(Proiect proiect){
        return ProiectUpdateDTO.builder()
                .id(proiect.getId())
                .client(proiect.getClient())
                .nrComandaInt(proiect.getNrComandaInt())
                .nrComandaClient(proiect.getNrComandaClient())
                .valoareFactura(proiect.getValoareFactura())
                .valoareFacturata(proiect.getValoareFacturata())
                .greutate(proiect.getGreutate())
                .transportator(proiect.getTransportator())
                .adresaLivrare(proiect.getAdresaLivrare())
                .dataCODE(proiect.getYearAndMonthAndDayDataCODE())
                .dataLivrare(proiect.getYearAndMonthAndDayDataLivrare())
                .dataPlecare(proiect.getYearAndMonthAndDayDataPlecare())
                .dataFactura(proiect.getYearAndMonthAndDayDataFactura())
                .nrFactura(proiect.getNrFactura())
                .build();
    }
    public Proiect maptoProiect(ProiectUpdateDTO proiectUpdateDTO){
        return Proiect.builder()
                .id(proiectUpdateDTO.getId())
                .client(proiectUpdateDTO.getClient())
                .nrComandaInt(proiectUpdateDTO.getNrComandaInt())
                .nrComandaClient(proiectUpdateDTO.getNrComandaClient())
                .valoareFactura(proiectUpdateDTO.getValoareFactura())
                .valoareFacturata(proiectUpdateDTO.getValoareFacturata())
                .greutate(proiectUpdateDTO.getGreutate())
                .transportator(proiectUpdateDTO.getTransportator())
                .adresaLivrare(proiectUpdateDTO.getAdresaLivrare())
                .nrFactura(proiectUpdateDTO.getNrFactura())
                .dataCODE(proiectUpdateDTO.stringToLocalDate(proiectUpdateDTO.getDataCODE()))
                .dataLivrare(proiectUpdateDTO.stringToLocalDate(proiectUpdateDTO.getDataLivrare()))
                .dataFactura(proiectUpdateDTO.stringToLocalDate(proiectUpdateDTO.getDataFactura()))
                .dataPlecare(proiectUpdateDTO.stringToLocalDate(proiectUpdateDTO.getDataPlecare()))
                .build();
    }
}
