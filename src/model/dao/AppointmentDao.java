package model.dao;

import java.util.List;

import model.entities.Appointment;

public interface AppointmentDao {

	void insert(Appointment obj);
			
	List<Appointment> findAll();
	
}
