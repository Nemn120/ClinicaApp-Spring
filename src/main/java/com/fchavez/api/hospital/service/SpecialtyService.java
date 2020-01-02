package com.fchavez.api.hospital.service;

import java.util.List;

import com.fchavez.api.hospital.model.Specialty;

public interface SpecialtyService extends CrudService<Specialty>{
	
	public List<Specialty> findByNombre(String term);

}
