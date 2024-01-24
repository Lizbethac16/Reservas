package package com.mycompany.citas.Cita;

import org.springframework.data.repository.CrudRepository;

public interface CitaRepository extends CrudRepository<Cita,String>,CustomRepository,CustomTwo{

	
	
}
