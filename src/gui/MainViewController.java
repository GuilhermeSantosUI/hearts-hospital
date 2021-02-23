package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.entities.Appointment;
import model.entities.Doctor;
import model.entities.Patient;
import model.services.AppointmentService;
import model.services.DoctorService;
import model.services.PatientService;

public class MainViewController implements Initializable {

	private DoctorService service = new DoctorService();
	private PatientService service2 = new PatientService();
	private AppointmentService service3 = new AppointmentService();

	@FXML
	private TextField txtCRM;

	@FXML
	private PasswordField txtPass;

	@FXML
	private Button btLogIn;

	public void setService(DoctorService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Doctor> list = service.findAll();
		List<Patient> list2 = service2.findAll();
		List<Appointment> list3 = service3.findAll();
		list.forEach(x -> System.out.println(x.toString()));
		list2.forEach(x -> System.out.println(x.toString()));
		list3.forEach(x -> System.out.println(x.toString()));
		handleInitializeNodes();
	}

	private void handleInitializeNodes() {
		Constraints.setTextFieldInteger(txtCRM);
	}

	@FXML
	public void handleSubmit(ActionEvent event) {
		Integer crm = null;
		if (txtCRM.getText().length() > 0) {
			crm = Integer.parseInt(txtCRM.getText());
		}
		service.handleLogin(crm, txtPass.getText());
	}
}
