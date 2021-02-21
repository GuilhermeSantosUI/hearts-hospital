package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DoctorDao;
import model.entities.Doctor;

public class DoctorService {

	private DoctorDao dao = DaoFactory.createDoctorDao();
	
	public List<Doctor> findAll(){
		return dao.findAll();
	}
	
	public void remove(Doctor obj) {
		dao.deleteById(obj.getCrm());
	}

}
