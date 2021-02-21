package model.dao.impl;

import java.sql.Connection;
import java.util.List;

import model.dao.AppointmentDao;
import model.entities.Appointment;

public class AppointmentDaoJDBC implements AppointmentDao {

	private Connection conn;

	public AppointmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(AppointmentDao obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById() {
		// TODO Auto-generated method stub

	}

	@Override
	public Appointment findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Appointment> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
