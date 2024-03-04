package com.ECPI.pontaj_application.repository;

import com.ECPI.pontaj_application.entity.Angajat;
import com.ECPI.pontaj_application.entity.Proiect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Repository
public interface ProiectRepository extends JpaRepository<Proiect,UUID>{

    List<Proiect> findAllByNrComandaIntContainingIgnoreCaseOrClientContainingIgnoreCaseOrDataCODEOrNrComandaClientContainingIgnoreCase(String searchItem1, String searchItem2, LocalDate searchItem3, String searchItem4);

    @Query(value = "SELECT p FROM Proiect p WHERE MONTH(p.dataCODE) = :month")
    List<Proiect> findAllByMonth(int month);
}
