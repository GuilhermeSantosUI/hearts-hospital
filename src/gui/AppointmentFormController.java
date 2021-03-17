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
import javafx.scene.control.TextField;
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
	private Label doctorError;

	@FXML
	private Label patientError;

	@FXML
	private ComboBox<Doctor> cbDoctor;

	@FXML
	private DatePicker dpDate;

	@FXML
	private ComboBox<Patient> cbPatient;

	@FXML
	private TextArea txtDescription;
	
	@FXML
	private TextField txtId;
			
	private ObservableList<Doctor> obsListDoctor;

	private ObservableList<Patient> obsListPatient;

	private DoctorService doctorService = new DoctorService();

	private PatientService patientService = new PatientService();

	private Appointment entity = new Appointment();

	private AppointmentService service = new AppointmentService();
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	public void setServices(AppointmentService service, DoctorService doctorService, PatientService patientService) {
		this.service = service;
		this.doctorService = doctorService;
		this.patientService = patientService;
	}

	public void setAppointment(Appointment entity) {
		this.entity = entity;
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	public void handleLoadAssociatedObjects() {
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
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
			VistaNavigator.loadVista(VistaNavigator.APPOINTMENTLIST);
		} catch (ValidateException e) {
			setErrorMessages(e.getErrors());
			e.printStackTrace();
		} catch (DbException e) {
			e.printStackTrace();
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}

	private Appointment getData() {
		Appointment appoint = new Appointment();
		ValidateException exception = new ValidateException("Validation error");
		
		appoint.setIdconsulta(entity.getIdconsulta());
		if (cbDoctor.getValue() == null) {
			exception.addError("medicoid", "Field can't be empty");
		} else {
			appoint.setMedicoid(cbDoctor.getValue());
		}
		if (dpDate.getValue() == null) {
			exception.addError("dataconsulta", "Field can't be empty");
		} else {
			LocalDate localDate = dpDate.getValue();
			java.util.Date date = java.sql.Date.valueOf(localDate);

			appoint.setDataconsulta(date);
		}
		if (cbPatient.getValue() == null) {
			exception.addError("idpaciente", "Field can't be empty");
		} else {
			appoint.setPacienteid(cbPatient.getValue());
		}

		if (txtDescription.getText() == null || txtDescription.getText().trim().equals("")) {
			exception.addError("descricao", "Field can't be empty");
		}
		appoint.setDescricao(txtDescription.getText());
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return appoint;
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		doctorError.setText((fields.contains("medicoid") ? errors.get("medicoid") : ""));
		dateError.setText((fields.contains("dataconsulta") ? errors.get("dataconsulta") : ""));
		patientError.setText((fields.contains("idpaciente") ? errors.get("idpaciente") : ""));
		descError.setText((fields.contains("descricao") ? errors.get("descricao") : ""));
	}

	public void handleUpdateData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (entity.getMedicoid() == null) {
			cbDoctor.getSelectionModel().selectFirst();
		} else {
			cbDoctor.setValue(entity.getMedicoid());
		}

		Date x = entity.getDataconsulta();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (x != null) {
			String s = sdf.format(x.getTime());
			dpDate.setValue(LOCAL_DATE(String.valueOf(s)));
		}

		if (entity.getPacienteid() == null) {
			cbPatient.getSelectionModel().selectFirst();
		} else {
			cbPatient.setValue(entity.getPacienteid());
		}
		
		txtDescription.setText(entity.getDescricao());
	}

	private final LocalDate LOCAL_DATE(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate localDate = LocalDate.parse(dateString, formatter);
		return localDate;
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

	private void initializeComboBoxPatient() {
		Callback<ListView<Patient>, ListCell<Patient>> factory = lv -> new ListCell<Patient>() {

			@Override
			protected void updateItem(Patient item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNome());

			}
		};

		cbPatient.setCellFactory(factory);
		cbPatient.setButtonCell(factory.call(null));

	}
	
	private void initializeNodes() {
		handleLoadAssociatedObjects();
		initializeComboBoxDoctor();
		initializeComboBoxPatient();
		txtDescription.setWrapText(true);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	

}
