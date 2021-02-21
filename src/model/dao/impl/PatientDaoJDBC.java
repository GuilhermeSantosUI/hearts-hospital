package model.dao.impl;

import java.sql.Connection;
import java.util.List;

import model.dao.PatientDao;
import model.entities.Patient;

public class PatientDaoJDBC implements PatientDao {

	private Connection conn;

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
	public Patient findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Patient> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
