package model.dao;

import java.util.List;

import model.entities.Appointment;

public interface AppointmentDao {

	void insert(AppointmentDao obj);
	
	void deleteById();
	
	Appointment findById(Integer id);
	
	List<Appointment> findAll();
	
}
