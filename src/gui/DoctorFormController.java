package gui;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import application.VistaNavigator;
import db.DbException;
import gui.listener.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Doctor;
import model.exceptions.ValidateException;
import model.services.DoctorService;

public class DoctorFormController implements Initializable {

	@FXML
	private TextField txtName;

	@FXML
	private Label nameError;

	@FXML
	private TextField txtCpf;

	@FXML
	private Label cpfError;

	@FXML
	private TextField txtCrm;

	@FXML
	private Label crmError;

	@FXML
	private TextField txtCell;

	@FXML
	private Label cellError;

	@FXML
	private Label birthError;

	@FXML
	private TextField txtEmail;

	@FXML
	private Label emailError;

	@FXML
	private TextField txtPass;

	@FXML
	private Label passError;

	@FXML
	private DatePicker dpBirth;

	private Doctor entity = new Doctor();

	private DoctorService service = new DoctorService();

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	public void setServices(DoctorService service) {
		this.service = service;
	}

	public void setDoctor(Doctor entity) {
		this.entity = entity;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Constraints.setTextFieldMaxLength(txtCrm, 5);
		Constraints.setTextFieldMaxLength(txtCpf, 11);
		Constraints.setTextFieldMaxLength(txtCell, 11);
	}

	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (entity == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
			VistaNavigator.loadVista(VistaNavigator.DOCTORLIST);
		} catch (ValidateException e) {
			setErrorMessages(e.getErrors());
		} catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}

	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	private Doctor getData() {
		Doctor doc = new Doctor();
		ValidateException exception = new ValidateException("Validation error");
		doc.setIdmedico(entity.getIdmedico());
		if (txtCrm.getText().isEmpty() || txtCrm.getText().trim().equals("")) {
			exception.addError("crm", "Field can't be empty");
		} else {
			doc.setCrm(Integer.parseInt(txtCrm.getText()));
		}
		if (txtName.getText().isEmpty() || txtName.getText().trim().equals("")) {
			exception.addError("nome", "Field can't be empty");
		}
		doc.setNomemed(txtName.getText());
		if (txtCpf.getText().isEmpty() || txtCpf.getText().trim().equals("")) {
			exception.addError("cpf", "Field can't be empty");
		}
		if (Utils.isCPF(txtCpf.getText())) {
			doc.setCpf(txtCpf.getText());
		} else {
			exception.addError("cpf", "this CPF is invalid");
		}
		if (txtEmail.getText().isEmpty() || txtEmail.getText().trim().equals("")) {
			exception.addError("email", "Field can't be empty");
		}
		doc.setEmailmed(txtEmail.getText());
		if (txtCell.getText().isEmpty() || txtCell.getText().trim().equals("") && txtCell.getText().length() < 11) {
			exception.addError("numcelular", "Field can't be empty");
		}
		doc.setNumcelularmed(txtCell.getText());
		if (dpBirth.getValue() == null) {
			exception.addError("datanascimento", "Field can't be empty");
		} else {
			LocalDate localDate = dpBirth.getValue();
			java.util.Date date = java.sql.Date.valueOf(localDate);
			doc.setDatanascimentomed(date);
		}
		if (txtPass.getText().isEmpty() || txtPass.getText().trim().equals("")) {
			exception.addError("senha", "Field can't be empty");
		}
		doc.setSenha(txtPass.getText());

		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		return doc;
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		crmError.setText((fields.contains("crm") ? errors.get("crm") : ""));
		nameError.setText((fields.contains("nome") ? errors.get("nome") : ""));
		cpfError.setText((fields.contains("cpf") ? errors.get("cpf") : ""));
		emailError.setText((fields.contains("email") ? errors.get("email") : ""));
		cellError.setText((fields.contains("numcelular") ? errors.get("numcelular") : ""));
		birthError.setText((fields.contains("datanascimento") ? errors.get("datanascimento") : ""));
		passError.setText((fields.contains("senha") ? errors.get("senha") : ""));
	}

	public void handleUpdateData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtCrm.setText(entity.getCrm().toString());
		txtName.setText(entity.getNomemed());
		txtCpf.setText(entity.getCpf());
		txtEmail.setText(entity.getEmailmed());
		txtCell.setText(entity.getNumcelularmed());
		Date x = entity.getDatanascimentomed();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (x != null) {
			String s = sdf.format(x.getTime());
			dpBirth.setValue(LOCAL_DATE(String.valueOf(s)));
		}
		txtPass.setText(entity.getSenha());

	}

	private final LocalDate LOCAL_DATE(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate localDate = LocalDate.parse(dateString, formatter);
		return localDate;
	}

}
