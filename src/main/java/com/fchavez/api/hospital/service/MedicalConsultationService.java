package com.fchavez.api.hospital.service;

import java.util.List;


import com.fchavez.api.hospital.model.MedicalConsultation;
import com.fchavez.api.hospital.model.Patient;


public interface MedicalConsultationService extends CrudService<MedicalConsultation>{
	List<MedicalConsultation> getByPatient(Patient paciente);

}


