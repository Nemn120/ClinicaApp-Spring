package com.fchavez.api.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fchavez.api.hospital.model.Doctor;
import com.fchavez.api.hospital.model.Patient;
import com.fchavez.api.hospital.model.Specialty;

public interface DoctorRepository extends JpaRepository<Doctor, Integer>{
	List<Doctor> findAllBySpecialty(Specialty specialty);
	List<Doctor> findByLastName(String lastName);
}
