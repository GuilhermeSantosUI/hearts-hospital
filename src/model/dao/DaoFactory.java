package model.dao;

import db.DB;
import model.dao.impl.AppointmentDaoJDBC;
import model.dao.impl.DoctorDaoJDBC;
import model.dao.impl.PatientDaoJDBC;

public class DaoFactory {

	public static AppointmentDao createApointmentDao() {
		return new AppointmentDaoJDBC(DB.getConnection());
	}
	
	public static DoctorDao createDoctorDao() {
		return new DoctorDaoJDBC(DB.getConnection());
	}
	
	public static PatientDao createPatientDao() {
		return new PatientDaoJDBC(DB.getConnection());
	}

}
