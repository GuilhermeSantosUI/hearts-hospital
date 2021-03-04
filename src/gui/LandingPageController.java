package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.entities.Appointment;
import model.services.AppointmentService;
import model.services.DoctorService;

public class LandingPageController implements Initializable {

	private DoctorService service = new DoctorService();
	private AppointmentService service3 = new AppointmentService();

	@FXML
	private TextField txtCRM;

	@FXML
	private PasswordField txtPass;

	@FXML
	private Button btLogIn;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Appointment> list = service3.findAll();
		list.forEach(x -> System.out.println(x.toString()));		
		handleInitializeNodes();
	}

	public void setService(DoctorService service) {
		this.service = service;
	}

	private void handleInitializeNodes() {
		Constraints.setTextFieldInteger(txtCRM);
	}

	@FXML
	public void handleSubmit() {
		service.handleLogin(txtCRM, txtPass);
	}

}