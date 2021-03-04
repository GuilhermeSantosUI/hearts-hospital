package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.VistaNavigator;
import gui.util.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import javafx.util.Callback;
import model.entities.Appointment;
import model.services.AppointmentService;

public class AppointmentListController implements Initializable {

	AppointmentService service = new AppointmentService();

	@FXML
	private VBox appointmentContainer;

	@FXML
	private TableView<Appointment> appointmentTable;

	@FXML
	private TableColumn<Appointment, Integer> crmColumn;

	@FXML
	private TableColumn<Appointment, String> doctorColumn;

	@FXML
	private TableColumn<Appointment, String> patientColumn;

	@FXML
	private TableColumn<Appointment, Date> dateColumn;

	@FXML
	private TableColumn<Appointment, String> descriptionColumn;

	@FXML
	private TextField txtSearch;

	private ObservableList<Appointment> obsList;

	@FXML
	void goBack() {
		VistaNavigator.loadVista(VistaNavigator.DASHBOARD);
	}

	@FXML
	private void handleOpenModal(MouseEvent event) {
		Dialog<ButtonType> dialog = new Dialog<>();
		Window window = dialog.getDialogPane().getScene().getWindow();
		window.setOnCloseRequest(e -> window.hide());
		dialog.initOwner(appointmentContainer.getScene().getWindow());
		dialog.setTitle("New appointment");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("AppointmentForm.fxml"));
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		dialog.setContentText(null);
		dialog.getDialogPane().getScene().getWindow();
		dialog.showAndWait();
	}

	private void handleSearchAppointment() {
		crmColumn.setCellValueFactory(new PropertyValueFactory<>("crm"));
		doctorColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment
	        		, String> param) {
	            return new SimpleStringProperty(param.getValue().getCrm().getNomemed());
	        }
	    });
		patientColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment
	        		, String> param) {
	            return new SimpleStringProperty(param.getValue().getIdpaciente().getNome());
	        }
	    });
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("dataconsulta"));
		Utils.formatTableColumnDate(dateColumn, "dd/MM/yyyy");
		List<Appointment> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		FilteredList<Appointment> filteredData = new FilteredList<>(obsList, b -> true);
		txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(patient -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (patient.getIdpaciente().getNome().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches username
				} else {
					return false; // Does not match.
				}

			});
		});
		SortedList<Appointment> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(appointmentTable.comparatorProperty());
		appointmentTable.setItems(sortedData);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		handleSearchAppointment();
	}

}
