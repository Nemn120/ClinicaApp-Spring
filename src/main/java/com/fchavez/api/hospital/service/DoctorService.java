package com.fchavez.api.hospital.service;

import java.util.List;

import com.fchavez.api.hospital.model.Doctor;
import com.fchavez.api.hospital.model.Patient;
import com.fchavez.api.hospital.model.Specialty;

public interface DoctorService extends CrudService<Doctor>{
	List<Doctor> getBySpecialty(Specialty specialty);
	List<Doctor> fetchDoctorByLastName(String lastName) throws Exception;
}
