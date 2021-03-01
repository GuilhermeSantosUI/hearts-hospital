package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.VistaNavigator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.entities.Doctor;
import model.services.DoctorService;

public class DashboardController implements Initializable {

	@FXML
	private Button exitButton;

	@FXML
	private Label doctorName, amountDoctors, doctorEmail, doctorCell;

	private DoctorService service = new DoctorService();
	private Doctor doctor = service.findBusyDoctor();

	@FXML
	void handleExitAcount() {
		VistaNavigator.loadVista(VistaNavigator.LANDING);
	}

	@FXML
	void handleGoDoc(MouseEvent event) {
		VistaNavigator.loadVista(VistaNavigator.DOCTORLIST);
	}

	@FXML
	void handleGoPatient(MouseEvent event) {
		VistaNavigator.loadVista(VistaNavigator.PATIENTLIST);
	}

	@FXML
	void handleGoAppointment(MouseEvent event) {
		VistaNavigator.loadVista(VistaNavigator.APPOINTMENTLIST);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		List<Doctor> list = service.findAll();
		doctorName.setText(doctor.getNome());
		doctorEmail.setText(doctor.getEmail());
		doctorCell.setText(doctor.getNumcelular());
		amountDoctors.setText(Integer.toString(list.size()));
	}

}
