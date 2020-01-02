package com.fchavez.api.hospital.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fchavez.api.hospital.model.DetailConsultation;
import com.fchavez.api.hospital.repository.DetailConsultationRepository;
import com.fchavez.api.hospital.service.DetailConsultationService;

@Service
public class DetailConsultationServiceImpl implements DetailConsultationService {

	@Autowired
	private DetailConsultationRepository detailConsultationRepository;
	
	@Transactional
	@Override
	public DetailConsultation insertOrUpdate(DetailConsultation entity) {
		return detailConsultationRepository.save(entity);
	}

	@Override
	public Optional<DetailConsultation> getOne(Integer id) {
		return detailConsultationRepository.findById(id);
	}

	@Override
	public List<DetailConsultation> getAll() {
		return detailConsultationRepository.findAll();
	}
	@Transactional
	@Override
	public void delete(Integer id) {
		detailConsultationRepository.deleteById(id);
		
	}
	
	

}
