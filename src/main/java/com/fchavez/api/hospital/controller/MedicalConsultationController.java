package com.fchavez.api.hospital.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fchavez.api.hospital.model.DetailConsultation;
import com.fchavez.api.hospital.model.Doctor;
import com.fchavez.api.hospital.model.MedicalConsultation;
import com.fchavez.api.hospital.model.Patient;
import com.fchavez.api.hospital.service.DoctorService;
import com.fchavez.api.hospital.service.MedicalConsultationService;
import com.fchavez.api.hospital.service.PatientService;



@Controller
@SessionAttributes("medical")
@RequestMapping("/medicalConsultation")
public class MedicalConsultationController {

	@Autowired
	PatientService patientService;
	
	@Autowired
	MedicalConsultationService medicalConsultationService;
	
	@Autowired
	DoctorService doctorService;
	
	Integer idPatient=0;
	
	
	@GetMapping("/generar")
	public String generar(Model model) {
		String DNI=null;
		model.addAttribute("DNI",DNI);
		model.addAttribute("titulo","Generar Nueva Consulta Medica");
		return "medicalCOnsultation/generar";
	}
	
	@PostMapping("/generar")
	public String generar(String dni,Model model,RedirectAttributes flash) throws Exception {
		List<Patient> pacientes=patientService.fetchPatientByDni(dni);
		if(pacientes.isEmpty()) {
			flash.addFlashAttribute("error", "El DNI del paciente no existe en la BBDD!");
			return "redirect:/medicalConsultation/generar";
		}
		return "redirect:/medicalConsultation/form/"+pacientes.get(0).getId(); 
	}
	
	
	@GetMapping("/form/{id}")
	public String crear(@PathVariable(value="id") Integer id,Model model,RedirectAttributes flash) {
		
		Optional<Patient> paciente=patientService.getOne(id);
		if(!paciente.isPresent()) {
			flash.addFlashAttribute("error", "El ID del cliente no existe en la BBDD!");
			return "redirect:/patient/listar";
		}
		idPatient=id;
		
		MedicalConsultation medConsultation = new MedicalConsultation();
		Patient paciente_=paciente.get();
		medConsultation.setPatient(paciente_);
		model.addAttribute("titulo","Nueva Consulta Medica");
		model.addAttribute("consulta",medConsultation);
		model.addAttribute("doctores",doctorService.getAll());
		System.out.println("CLIENTE: "+medConsultation.getPatient().getId());
		return "medicalConsultation/form";
		
	}
	
	
	@PostMapping("/form")
	public String guardar(MedicalConsultation medConsultation, Model model,
			@RequestParam(name = "diagnostico[]", required = true) String[] diagnostico,
			@RequestParam(name = "tratamiento[]", required = true) String[] tratamiento,
			SessionStatus status
			) {
		
		if (tratamiento == null || tratamiento.length == 0) {
			System.out.println("CONSULTA VACIA");
			model.addAttribute("info", "La Consulta no tiene Detalle");
			return "medicalConsultation/form";
		}
		//
		
		for (int i = 0; i < diagnostico.length; i++) {
			
			DetailConsultation detalleConsulta = new DetailConsultation();
			System.out.println("CONTADOR: "+i);
			detalleConsulta.setDiagnostic(diagnostico[i]);
			detalleConsulta.setTreatment(tratamiento[i]);
			medConsultation.addDetailConsultation(detalleConsulta);
			detalleConsulta.setMedicalConsultation(medConsultation);
			
		}
		
		model.addAttribute("titulo","Guardar Consulta Medica");
		Optional<Doctor>doctor=doctorService.getOne(medConsultation.getDoctor().getId());
		if(doctor.isPresent()) {
			doctor.get().addMedicalConsultations(medConsultation);
			medConsultation.setDoctor(doctor.get());
		}	
		
		Optional<Patient> paciente=patientService.getOne(idPatient);
		if(!paciente.isPresent()) {
			
			return "redirect:/patient/listar";
		}
		medConsultation.setPatient(paciente.get());
		paciente.get().addMedicalConsultations(medConsultation);
		medicalConsultationService.insertOrUpdate(medConsultation);
		
		status.setComplete();
		return "redirect:/patient/listar";
	}

	
	@GetMapping("/listar")
	public String listar(Model model) {
		model.addAttribute("titulo","Lista de Consultas Medicas");
		List<MedicalConsultation> medConsultas=medicalConsultationService.getAll();
		model.addAttribute("titulo","Lista de consultas medicas");
		model.addAttribute("medConsultas",medConsultas);
		return "medicalConsultation/listar";
	}
	
	@GetMapping("/eliminar/{id}")
	public String delete(@PathVariable(value="id") Integer id) {
		if(id>0)	
		this.medicalConsultationService.delete(id);
		return "redirect:/patient/listar";
	}
	
	
	
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value="id")Integer id, Model model,RedirectAttributes flash) {
		Optional<MedicalConsultation> consultaMedica=medicalConsultationService.getOne(id);
		if(!consultaMedica.isPresent()) {
			flash.addFlashAttribute("error", "Consulta Medica no existe");
			return "redirect:/patient/listar"; 
			
		}
		model.addAttribute("consulta",consultaMedica.get());
		model.addAttribute("titulo","Consulta Medica Paciente: "+consultaMedica.get().getPatient().getFirstName() + " "+consultaMedica.get().getPatient().getLastName());
		return "/medicalConsultation/ver";
		
	}
	@GetMapping("/buscar")
	public String buscar(Model model) throws Exception {
		String DNI = null;
		
		model.addAttribute("DNI",DNI);
		model.addAttribute("titulo","Mostrar Consulta de pacientes por DNI");
		return "medicalConsultation/buscar";
	}
	
	
	@PostMapping("/buscar")
	public String buscarpost(String dni,Model model,RedirectAttributes flash) throws Exception {
		List<Patient> pacientes=patientService.fetchPatientByDni(dni);
		if(pacientes.isEmpty()) {
			flash.addFlashAttribute("error", "El DNI del paciente no existe en la BBDD!");
			return "redirect:/medicalConsultation/buscar";
		}
		List<MedicalConsultation> listaConsultas = medicalConsultationService.getByPatient(pacientes.get(0));
		
		model.addAttribute("medConsultas",listaConsultas);
		model.addAttribute("titulo","Consultas Medicas del Paciente "+pacientes.get(0).getFirstName()+ " "+ pacientes.get(0).getLastName());
		return "medicalConsultation/listar";
	}
	
	
	

}
