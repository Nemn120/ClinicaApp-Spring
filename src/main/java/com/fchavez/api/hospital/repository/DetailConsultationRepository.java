package com.fchavez.api.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fchavez.api.hospital.model.DetailConsultation;

public interface DetailConsultationRepository extends  JpaRepository<DetailConsultation,Integer>{
	
	

}
