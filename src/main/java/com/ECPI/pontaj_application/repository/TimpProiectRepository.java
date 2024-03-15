package com.ECPI.pontaj_application.repository;

import com.ECPI.pontaj_application.entity.TimpProiect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Repository
public interface TimpProiectRepository extends JpaRepository<TimpProiect,Integer> {

    List<TimpProiect> findByAngajatId(UUID angajatId);

    @Query(value = "SELECT p FROM TimpProiect p WHERE MONTH(p.data) = :month")
    List<TimpProiect> findAllByMonth(int month);

    @Query(value = "SELECT p FROM TimpProiect p WHERE p.data = :date")
    List<TimpProiect> findAllByDate(LocalDate date);

    @Query(value = "SELECT p FROM TimpProiect p WHERE MONTH(p.data) = :month AND p.angajat.id = :angajatId")
    List<TimpProiect> findAllByMonthAndAngajatId(int month, UUID angajatId);


}
