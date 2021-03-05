package gui;

import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listener.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.util.Callback;
import model.entities.Appointment;
import model.entities.Doctor;
import model.entities.Patient;
import model.exceptions.ValidateException;
import model.services.AppointmentService;
import model.services.DoctorService;
import model.services.PatientService;

public class AppointmentFormController implements Initializable {

	@FXML
	private Label dateError;

	@FXML
	private Label descError;

	@FXML
	private ComboBox<Doctor> cbDoctor;

	@FXML
	private DatePicker dpDate;

	@FXML
	private ComboBox<Patient> cbPatient;

	@FXML
	private TextArea txtDescription;

	private ObservableList<Doctor> obsListDoctor;

	private ObservableList<Patient> obsListPatient;

	private DoctorService doctorService = new DoctorService();

	private PatientService patientService = new PatientService();

	private Appointment entity = new Appointment();

	private AppointmentService service = new AppointmentService();
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	public void loadAssociatedObjects() {
		if (doctorService == null || patientService == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Doctor> listDoc = doctorService.findAll();
		obsListDoctor = FXCollections.observableArrayList(listDoc);
		cbDoctor.setItems(obsListDoctor);
		
		List<Patient> listPat = patientService.findAll();
		obsListPatient = FXCollections.observableArrayList(listPat);
		cbPatient.setItems(obsListPatient);
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
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
			System.out.println(getData().toString());
			service.saveData(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		} catch (ValidateException e) {
			setErrorMessages(e.getErrors());
		} catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}

	private Appointment getData() {
		Appointment appoint = new Appointment();
		ValidateException exception = new ValidateException("Validation error");
		appoint.setCrm(cbDoctor.getValue());

		if (dpDate.getValue() == null) {
			exception.addError("appointmentDate", "Field can't be empty");
		} else {
			Instant instant = Instant.from(dpDate.getValue().atStartOfDay(ZoneId.systemDefault()));
			Date x = Date.from(instant);
			appoint.setDataconsulta(x);
		}

		appoint.setIdpaciente(cbPatient.getValue());

		if (txtDescription.getText() == null || txtDescription.getText().trim().equals("")) {
			exception.addError("descArea", "Field can't be empty");
		}
		appoint.setDescricao(txtDescription.getText());

		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		System.out.println(appoint.toString());
		return appoint;
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		dateError.setText((fields.contains("appointmentDate") ? errors.get("appointmentDate") : ""));
		descError.setText((fields.contains("descArea") ? errors.get("descArea") : ""));
	}
	
	private void initializeComboBoxDoctor() {
		Callback<ListView<Doctor>, ListCell<Doctor>> factory = lv -> new ListCell<Doctor>() {
			@Override
			protected void updateItem(Doctor item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNomemed());

			}
		};

		cbDoctor.setCellFactory(factory);
		cbDoctor.setButtonCell(factory.call(null));
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		initializeComboBoxDoctor();
	}

	private void initializeNodes() {
		loadAssociatedObjects();
		new AutoCompleteComboBoxListener<>(cbDoctor);
		new AutoCompleteComboBoxListener<>(cbPatient);
		txtDescription.setWrapText(true);
	}

}
