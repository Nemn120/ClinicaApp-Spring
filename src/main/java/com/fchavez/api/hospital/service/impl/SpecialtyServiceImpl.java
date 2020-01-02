package com.fchavez.api.hospital.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fchavez.api.hospital.model.Specialty;
import com.fchavez.api.hospital.repository.SpecialtyRepository;
import com.fchavez.api.hospital.service.SpecialtyService;

@Service
public class SpecialtyServiceImpl implements SpecialtyService{
	
	@Autowired
	private SpecialtyRepository specialtyRepository;
	
	@Transactional
	@Override
	public Specialty insertOrUpdate(Specialty entity) {
		return specialtyRepository.save(entity);
		
	}

	@Override
	public Optional<Specialty> getOne(Integer id) {
		return specialtyRepository.findById(id);
	}

	@Override
	public List<Specialty> getAll() {
		return (List<Specialty>) specialtyRepository.findAll();
	}
	@Transactional
	@Override
	public void delete(Integer id) {
		specialtyRepository.deleteById(id);
		
	}

	@Override
	public List<Specialty> findByNombre(String term) {
		return specialtyRepository.findIsLikeNombreOrderByNombre(term);
	}
	
	

}
