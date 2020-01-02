package com.fchavez.api.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fchavez.api.hospital.model.MedicalConsultation;
import com.fchavez.api.hospital.model.Patient;


public interface MedicalConsultationRepository  extends JpaRepository<MedicalConsultation, Integer>{
		
	List<MedicalConsultation> findAllByPatient(Patient patient);
}
