package com.fchavez.api.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fchavez.api.hospital.model.Patient;
import com.fchavez.api.hospital.model.Specialty;

public interface PatientRepository extends JpaRepository<Patient,Integer> {
	
	List<Patient> findByDni(String dni);

}
