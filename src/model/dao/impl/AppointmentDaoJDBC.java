package model.dao.impl;

import java.sql.Connection;
import java.util.List;

import db.DB;
import model.dao.AppointmentDao;
import model.entities.Appointment;

public class AppointmentDaoJDBC implements AppointmentDao {

	DB con = new DB();
	@SuppressWarnings("static-access")
	private Connection conn = con.getConnection();

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
	public List<Appointment> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
