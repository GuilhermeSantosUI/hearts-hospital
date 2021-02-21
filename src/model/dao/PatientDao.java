package model.dao;

import java.util.List;

import model.entities.Patient;

public interface PatientDao {
	
	void insert(PatientDao obj);
	
	void deleteById(PatientDao obj);
	
	List<Patient> findAll();

}
