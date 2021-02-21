package model.dao;

import java.util.List;

import model.entities.Doctor;

public interface DoctorDao {
	
	void insert(Doctor obj);
	
	void deleteById(Integer id);
		
	List<Doctor> findAll();
	
	void handleLogin(Integer crmDoctor, String passDoctor);

}
