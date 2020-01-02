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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fchavez.api.hospital.model.Doctor;
import com.fchavez.api.hospital.model.Patient;
import com.fchavez.api.hospital.model.Specialty;
import com.fchavez.api.hospital.service.DoctorService;
import com.fchavez.api.hospital.service.MedicalConsultationService;
import com.fchavez.api.hospital.service.PatientService;
import com.fchavez.api.hospital.service.SpecialtyService;


@Controller
@SessionAttributes("patient")
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	PatientService patientService;
	
	@Autowired
	MedicalConsultationService medicalConsultationService;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("titulo", "APP CLINICA MODERNS");
		return "patient/index";
	}
	@GetMapping("/form")
	public String crear(Model model) {
		model.addAttribute("titulo","Registrar Paciente");
		model.addAttribute("patient",new Patient());
		return "patient/form";
		
	}
	@PostMapping("/form")
	public String guardar(Patient patient, Model model) {
		
		model.addAttribute("titulo","Guardar Paciente");
		patientService.insertOrUpdate(patient);
		return "redirect:/patient/listar";
	}
	@GetMapping("/form/{id}")
	public String editar(@PathVariable(value="id") Integer id,  Model model,RedirectAttributes flash) {
		model.addAttribute("titulo","Editar Paciente");
		Optional<Patient> paciente=null;
		if(id>0) {
			paciente=patientService.getOne(id);
			if(!paciente.isPresent()) {
				flash.addFlashAttribute("error", "El ID del cliente no existe en la BBDD!");
				return "redirect:/patient/listar";
			}
		}else {
			flash.addFlashAttribute("error", "El ID del cliente no puede ser cero!");
			return "redirect:/patient/listar";
		}
		model.addAttribute("patient",paciente);
		return "patient/form";
	}
	
	@GetMapping("/listar")
	public String listar(Model model) {
		model.addAttribute("titulo","Lista de Pacientes");
		List<Patient> pacientes=patientService.getAll();
		String DNI=null;
		model.addAttribute("DNI",DNI);
		model.addAttribute("patients",pacientes);
		return "patient/listar";
	}
	
	
	@GetMapping("/eliminar/{id}")
	public String delete(@PathVariable(value="id") Integer id) {
		if(id>0)
		this.patientService.delete(id);
		return "redirect:/patient/listar";
	}
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value="id")Integer id, Model model,RedirectAttributes flash) {
		Optional<Patient>paciente=patientService.getOne(id);
		if(!paciente.isPresent()) {
			flash.addFlashAttribute("error", "Paciente no existe");
			return "redirect:/patient/listar"; 
			
		}
		model.addAttribute("titulo","Ver detalle paciente");
		model.addAttribute("patient",paciente.get());
		return "patient/ver";
		
	}
	@PostMapping("/buscar")
	public String buscar(String dni,Model model,RedirectAttributes flash) throws Exception {
		if(dni.isEmpty()) {
			flash.addFlashAttribute("error", "Ingrese DNI valido");
			return "redirect:/patient/buscar"; 
		}
		List<Patient> pacientes=patientService.fetchPatientByDni(dni);
		if(pacientes.isEmpty()) {
			flash.addFlashAttribute("error", "Paciente no existe");
			return "redirect:/patient/buscar"; 
			
		}
		
		
		return "redirect:/patient/ver/"+pacientes.get(0).getId(); 
	}
	

	
	@GetMapping("/buscar")
	public String buscar(Model model) throws Exception {
		String DNI = null;
		
		model.addAttribute("DNI",DNI);
		model.addAttribute("titulo","Buscar Historia Clinica por DNI");
		return "patient/buscar";
	}
	
	
	
	
	

}
