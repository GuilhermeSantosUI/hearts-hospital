package model.dao;

import java.util.List;

import model.entities.Patient;

public interface PatientDao {
	
	void insert(PatientDao obj);
	void deleteById(PatientDao obj);
	
	Patient findById(Integer id);
	List<Patient> findAll();

}
