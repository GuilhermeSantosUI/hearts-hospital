package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.services.DoctorService;

public class LandingPageController implements Initializable {

	private DoctorService service = new DoctorService();

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