
package com.fchavez.api.hospital.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="medical_consultations")
public class MedicalConsultation implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Date createDate;

	@ManyToOne
	private Patient patient;
	@ManyToOne
	private Doctor doctor;
	

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "medicalConsultation", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<DetailConsultation> detailConsultation;
	
	@PrePersist
	public void init() {
		this.createDate = new Date();
	}
	public MedicalConsultation() {
		detailConsultation = new ArrayList<DetailConsultation>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	public List<DetailConsultation> getDetailConsultation() {
		return detailConsultation;
	}
	public void setDetailConsultation(List<DetailConsultation> detailConsultation) {
		this.detailConsultation = detailConsultation;
	}
	public void addDetailConsultation(DetailConsultation detailConsultation) {
		this.detailConsultation.add(detailConsultation);
	}
	
	
	
	

}
