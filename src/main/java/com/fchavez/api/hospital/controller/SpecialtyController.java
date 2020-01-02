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
import com.fchavez.api.hospital.model.Specialty;
import com.fchavez.api.hospital.service.DoctorService;
import com.fchavez.api.hospital.service.SpecialtyService;

@Controller
@RequestMapping("/specialty")
@SessionAttributes("specialty")
public class SpecialtyController {
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
		model.addAttribute("titulo","Registrar Especialidad");
		model.addAttribute("specialty",new Specialty());
		return "specialty/form";
		
	}
	@PostMapping("/form")
	public String guardar(Specialty specialty, Model model) {
		
		model.addAttribute("titulo","Guardar Especialidad");
		specialtyService.insertOrUpdate(specialty);
		return "redirect:/specialty/listar";
	}
	@GetMapping("/form/{id}")
	public String editar(@PathVariable(value="id") Integer id,  Model model,RedirectAttributes flash) {
		model.addAttribute("titulo","Editar Especialidad");
		Optional<Specialty> specialty=null;
		if(id>0) {
			specialty=specialtyService.getOne(id);
			if(!specialty.isPresent()) {
				flash.addFlashAttribute("error", "El ID de la Especialidad no existe en la BBDD!");
				return "redirect:/specialty/listar";
			}
		}else {
			flash.addFlashAttribute("error", "El ID de la Especialidad no puede ser cero!");
			return "redirect:/specialty/listar";
		}
		model.addAttribute("specialty",specialty);
		return "specialty/form";
	}
	
	@GetMapping("/listar")
	public String listar(Model model) {
		model.addAttribute("titulo","Lista de Especialidades");
		List<Specialty> especialidades=specialtyService.getAll();
		model.addAttribute("especialidades",especialidades);
		return "specialty/listar";
	}
	
	
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value = "id") Integer id , Model model ) {
		Optional<Specialty> specialty = specialtyService.getOne(id);
		if(specialty.isPresent()) {
			model.addAttribute("specialty", specialty.get());
			model.addAttribute("titulo", "Busqueda de Doctores segun Especialidad");
			
		}
		return "specialty/ver";
	}
	
	@GetMapping("/eliminar/{id}")
	public String delete(@PathVariable(value="id") Integer id) {
		if(id>0)
		this.specialtyService.delete(id);
		return "redirect:/specialty/listar";
	}

	

}
