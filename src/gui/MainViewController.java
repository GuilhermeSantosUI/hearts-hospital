package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import model.entities.Doctor;
import model.services.DoctorService;

public class MainViewController implements Initializable {

	private DoctorService service = new DoctorService();

	public void setService(DoctorService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Doctor> list = service.findAll();
		list.forEach(x -> System.out.println(x.toString()));

	}

}
