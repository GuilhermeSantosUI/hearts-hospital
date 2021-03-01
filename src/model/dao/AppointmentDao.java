package model.dao;

import java.util.List;

import model.entities.Appointment;

public interface AppointmentDao {

	void insert(AppointmentDao obj);
			
	List<Appointment> findAll();
	
}
