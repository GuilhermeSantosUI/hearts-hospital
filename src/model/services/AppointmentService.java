package model.services;

import java.util.List;

import model.dao.AppointmentDao;
import model.dao.DaoFactory;
import model.entities.Appointment;

public class AppointmentService {

	private AppointmentDao dao = DaoFactory.createApointmentDao();

	public List<Appointment> findAll() {
		return dao.findAll();
	}

}
