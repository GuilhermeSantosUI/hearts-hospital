package gui;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PatientFormController {

	@FXML
	private Label genderError;

	@FXML
	private Label dateError;

	@FXML
	private Label nameError;

	@FXML
	private TextField txtName;

	@FXML
	private ComboBox<?> cbGender;

	@FXML
	private DatePicker dpBirth;

	@FXML
	private TextField txtAddress;

	@FXML
	private TextField txtCell;

	@FXML
	private Label cellError;

	@FXML
	private TextField txtTelephone;

	@FXML
	private Label teleError;

	@FXML
	private Label addressError;

	@FXML
	private TextField txtEmail;

	@FXML
	private Label emailError;
}
