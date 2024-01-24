package com.mycompany.citas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.citas.Cita.Cita;
import com.mycompany.citas.Cita.CitaRepository;
import com.mycompany.citas.Doctor.Doctor;
import com.mycompany.citas.Doctor.DoctorRepository;
import com.mycompany.citas.Paciente.Paciente;
import com.mycompany.citas.Paciente.PacienteRepository;

@Controller
public class ControllerClass {
	
	int count=0;
	
	@Autowired
	PacienteRepository pacienteRepo;
	
	@Autowired
	DoctorRepository docRepo;
	
	@Autowired
	CitaRepository appRepo;
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@GetMapping("/registerdoc")
	public String registerdoc() {
		return "registerdoc";
	}

	@GetMapping("/")
	public String home() {
		return "start";
	}

	@GetMapping("/patlog")
	public String patlog() {
		return "index";
	}

	@GetMapping("/doclog")
	public String doclog() {
		return "doclog";
	}
	
	@PostMapping("/registered")
	public String registered(Paciente paciente) {
		pacienteRepo.save(paciente);
		return "redirect:/";
	}

	@PostMapping("/registereddoc")
	public String registereddoc(Doctor doctor) {
		docRepo.save(doctor);
		return "redirect:/";
	}
	
	@GetMapping("/fail_login")
	public String fail_login() {
		return "fail_login";
	}

	@PostMapping("/authenticate")
	public String authenticate(Paciente paciente,HttpSession session) {
		if(pacienteRepo.existsById(paciente.getEmail()) && pacienteRepo.findById(paciente.getEmail()).get().getPassword().equals(paciente.getPassword())) {
			session.setAttribute("paciente", paciente.getEmail());
			return "redirect:/home";
			}
		return "redirect:/fail_login";
	}

	@PostMapping("/authenticatedoc")
	public String authenticatedoc(Doctor doctor,HttpSession session) {
		if(docRepo.existsById(doctor.getEmail()) && docRepo.findById(doctor.getEmail()).get().getPassword().equals(doctor.getPassword())) {
			session.setAttribute("doctor", doctor.getEmail());
			return "redirect:/patientlist";
			}
		return "redirect:/fail_login";
	}
	@GetMapping("/home")
	public ModelAndView display(HttpSession session) {
		ModelAndView mav= new ModelAndView("fail_login");
		String email = null;
		
		
		if(session.getAttribute("paciente")!=null) {
			mav = new ModelAndView("home");
		email = (String) session.getAttribute("paciente");
		}
		
		mav.addObject("email",email);
		
		return mav;
		
		
	}


	
	@PostMapping("/assignment")
	public String submitted(Cita cita) {
		cita.setAppId(count++);
		cita.setStatus("Active");
		citaRepo.save(cita);
		
//		System.out.println(cita.getEmail());
//		System.out.println(cita.getDate());
//		System.out.println(docRepo.findById(cita.getDocId()).get(0).getName());
		return "redirect:/docdetails";
	}
	
	@GetMapping("/docdetails")
	public ModelAndView DocDetails(HttpSession session) {
		
	    List<Doctor> doctors = new ArrayList<Doctor>();
		docRepo.findAll().forEach(doctors::add);
	    Map<String, Object> params = new HashMap<>();
	    
	    params.put("doctor", doctors);
	    params.put("email", session.getAttribute("paciente"));
	    
	    return new ModelAndView("doctorlist", params);
	}
	
	@GetMapping("/userdetails")
	public ModelAndView UserDetails(HttpSession session) {
		List<Cita> citas = citaRepo.findAllByEmail(session.getAttribute("paciente").toString());
		Map<String,Object> params = new HashMap<>();
		
		params.put("citas", citas);
		params.put("email", session.getAttribute("paciente"));
		
		return new ModelAndView("appointed",params);
		
		
	}
	@GetMapping("/patientlist")
	public ModelAndView PatientList(HttpSession session) {
		List<Cita> citas = citaRepo.findByDocId(session.getAttribute("doctor").toString());
		Map<String,Object> params = new HashMap<>();
		
		params.put("citas", citas);
		params.put("email", session.getAttribute("doctor"));
		
		return new ModelAndView("appointedDoc",params);
		
		
	}
	
	
	
	
}
