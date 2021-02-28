package model.dao;

import java.util.List;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.entities.Doctor;

public interface DoctorDao {
	
	void insert(Doctor obj);
	
	void deleteById(Integer id);
		
	List<Doctor> findAll();
	
	Doctor findById();
	
	void handleLogin(TextField crmDoctor, PasswordField passDoctor);

}
