package com.ECPI.pontaj_application.repository;

import com.ECPI.pontaj_application.entity.Angajat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AngajatRepository extends JpaRepository <Angajat, UUID> {

   List<Angajat> findByNumeContainingIgnoreCaseOrPrenumeContainingIgnoreCaseOrRolContainingIgnoreCase(String nume, String prenume, String rol);
}
