package model.services;

import java.util.List;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.dao.DaoFactory;
import model.dao.DoctorDao;
import model.entities.Doctor;

public class DoctorService {

	private DoctorDao dao = DaoFactory.createDoctorDao();

	public List<Doctor> findAll() {
		return dao.findAll();
	}

	public Doctor findById() {
		return dao.findById();
	}

	public void handleLogin(TextField crmDoctor, PasswordField passDoctor) {
		dao.handleLogin(crmDoctor, passDoctor);
	}

	public void remove(Doctor obj) {
		dao.deleteById(obj.getCrm());
	}

}
