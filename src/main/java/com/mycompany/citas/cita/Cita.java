package com.mycompany.citas.Cita;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Component
@Entity
public class Cita {
	@Id
	private String consultorio;
	private String docName;
	private String pacienteName;
	private String date;
//	private String slot;
	// Doctor ID	Doctor's Name	Specialization	Appointment Time	Appointment Date	Status
	public String getConsultorio() {
		return consultorio;
	}
	public void setConsultorio(String consultorio) {
		this.consultorio = consultorio;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
//	public String getSlot() {
//		return slot;
//	}
//	public void setSlot(String slot) {
//		this.slot = slot;
//	}
	
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getPacienteName() {
		return pacienteName;
	}
	public void setPacienteName(String pacienteName) {
		this.pacienteName = pacienteName;
	}



}
