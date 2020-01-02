package com.fchavez.api.hospital.service;

import java.util.List;

import com.fchavez.api.hospital.model.Patient;
import com.fchavez.api.hospital.model.Specialty;

public interface PatientService extends CrudService<Patient>{
	List<Patient> fetchPatientByDni(String dni) throws Exception;

}
