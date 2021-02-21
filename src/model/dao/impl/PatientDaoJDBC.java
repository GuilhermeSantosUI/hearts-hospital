package model.dao.impl;

import java.sql.Connection;
import java.util.List;

import db.DB;
import model.dao.PatientDao;
import model.entities.Patient;

public class PatientDaoJDBC implements PatientDao {

	DB con = new DB();
	@SuppressWarnings("static-access")
	private Connection conn = con.getConnection();

	public PatientDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(PatientDao obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(PatientDao obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Patient> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
