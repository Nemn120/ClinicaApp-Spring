package com.fchavez.api.hospital.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fchavez.api.hospital.model.Doctor;
import com.fchavez.api.hospital.model.Specialty;
import com.fchavez.api.hospital.service.DoctorService;
import com.fchavez.api.hospital.service.SpecialtyService;

@Controller
@RequestMapping("/doctor")
@SessionAttributes("doctor")
public class DoctorController{
	
	@Autowired
	DoctorService doctorService;
	
	@Autowired
	SpecialtyService specialtyService;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("titulo", "APP CLINICA MODERNS");
		return "doctor/index";
	}
	@GetMapping("/form")
	public String crear(Model model) {
		model.addAttribute("titulo","Registrar Doctor");
		model.addAttribute("doctor",new Doctor());
		List<Specialty> lista=specialtyService.getAll();
		model.addAttribute("specialtys",lista);
		return "doctor/form";
		
	}
	@PostMapping("/form")
	public String guardar(Doctor doctor, Model model) {
		
		
		model.addAttribute("titulo","Guardar Doctor");
		Optional<Specialty>especialidad=specialtyService.getOne(doctor.getSpecialty().getId());
		doctor.setSpecialty(especialidad.get());
		doctorService.insertOrUpdate(doctor);
		if(especialidad.isPresent()) {
			especialidad.get().addDoctor(doctor);
		}
		
		return "redirect:/doctor/listar";
	}
	@GetMapping("/form/{id}")
	public String editar(@PathVariable(value="id") Integer id,  Model model,RedirectAttributes flash) {
		model.addAttribute("titulo","Editar Doctor");
		Optional<Doctor> doctor=null;
		if(id>0) {
			doctor=doctorService.getOne(id);
			if(!doctor.isPresent()) {
				flash.addFlashAttribute("error", "El ID del cliente no existe en la BBDD!");
				return "redirect:/doctor/listar";
			}
		}else {
			flash.addFlashAttribute("error", "El ID del cliente no puede ser cero!");
			return "redirect:/listar";
		}
		model.addAttribute("specialtys",specialtyService.getAll());
		model.addAttribute("doctor",doctor);
		return "doctor/form";
	}
	
	@GetMapping("/listar")
	public String listar(Model model) {
		model.addAttribute("titulo","Lista de Doctores");
		List<Doctor> doctores=doctorService.getAll();
		String nombre=null;
		model.addAttribute("nombre",nombre);
		model.addAttribute("doctores",doctores);
		return "doctor/listar";
	}
	

	
	
	@GetMapping("/ver/{id}")
	public String verMedico(@PathVariable(value = "id") Integer id , Model model ) {
		Optional<Doctor> doctor = doctorService.getOne(id);
		if(doctor.isPresent()) {
			model.addAttribute("doctor", doctor.get());	
		}
		return "doctor/ver";
	}
	
	@GetMapping("/eliminar/{id}")
	public String delete(@PathVariable(value="id") Integer id) {
		if(id>0)
		this.doctorService.delete(id);
		return "redirect:/doctor/listar";
	}
	@GetMapping("/buscar")
	public String buscarDoctoresxEsp(Model model) {
		String nombre=null;
		model.addAttribute("nombre",nombre);
		List<Specialty> lista=specialtyService.getAll();
		model.addAttribute("specialtys",lista);
		model.addAttribute("titulo","Buscar Doctores por especialidad");
		return "specialty/buscarDoctor";
	}
	@PostMapping("/buscar")
	public String buscarDoctoresxEsp(String nombre,Model model) {
		
		System.out.println("Especialidad: "+nombre);
		Integer id=Integer.parseInt(nombre);
		Optional<Specialty> specialty=specialtyService.getOne(id);
		if(specialty.isPresent()) {
			return "redirect:/specialty/ver/"+specialty.get().getId();
		
		}
		return "specialty/buscarDoctor";
	}
	
	

	

	
	
	
	

}
