package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.VistaNavigator;
import gui.util.Alerts;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import model.entities.Appointment;
import model.services.AppointmentService;
import model.services.DoctorService;
import model.services.PatientService;

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

	public TableView<Appointment> getAppointmentTable() {
		return appointmentTable;
	}

	public void setAppointmentTable(TableView<Appointment> appointmentTable) {
		this.appointmentTable = appointmentTable;
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

	@FXML
	private void handleOpenModalEdit(MouseEvent event) throws IOException {
		if (appointmentTable.getSelectionModel().getSelectedItem() == null) {
			Alerts.showAlert("Erro ao editar a consulta!", null, "Selecione a consulta na tabela para poder editar!",
					AlertType.ERROR);
		} else if (appointmentTable.getSelectionModel().getSelectedItem() != null) {
			Dialog<ButtonType> dialog = new Dialog<>();
			Window window = dialog.getDialogPane().getScene().getWindow();
			window.setOnCloseRequest(e -> window.hide());
			dialog.initOwner(appointmentContainer.getScene().getWindow());
			dialog.setTitle("Edit appointment");
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AppointmentForm.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			AppointmentFormController controller = fxmlLoader.getController();
			controller.setAppointment(appointmentTable.getSelectionModel().getSelectedItem());
			controller.setServices(new AppointmentService(), new DoctorService(), new PatientService());
			controller.loadAssociatedObjects();
			controller.handleUpdateData();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();
			newStage.setScene(newScene);
			newStage.show();
			VistaNavigator.loadVista(VistaNavigator.APPOINTMENTLIST);
		}
	}

	private void handleSearchAppointment() {
		crmColumn.setCellValueFactory(new PropertyValueFactory<>("medicoid"));
		doctorColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> param) {
						return new SimpleStringProperty(param.getValue().getMedicoid().getNomemed());
					}
				});
		patientColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> param) {
						return new SimpleStringProperty(param.getValue().getPacienteid().getNome());
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

				if (patient.getPacienteid().getNome().toLowerCase().indexOf(lowerCaseFilter) != -1
						|| patient.getMedicoid().getNomemed().toLowerCase().indexOf(lowerCaseFilter) != -1) {
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

	@FXML
	private void handleRemoveSelectedValue(MouseEvent event) {
		if (appointmentTable.getSelectionModel().getSelectedItem() == null) {
			Alerts.showAlert("Erro ao remover a consulta!", null, "Selecione a consulta na tabela para poder deletar!",
					AlertType.ERROR);
		} else if (appointmentTable.getSelectionModel().getSelectedItem() != null) {
			Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
			try {
				service.remove(selectedAppointment);
				Alerts.showAlert("Consulta removida com sucesso!", null, "A consulta foi removida da tabela!",
						AlertType.CONFIRMATION);
				updateTableView();
			} catch (Exception e) {
				Alerts.showAlert("Tivemos algum problema!", "Parece que aconteceu algo inesperado no sistema!",
						"Tente novamente mais tarde!", AlertType.ERROR);
			}

		}
	}
	

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Appointment> list = service.findAll();
		this.obsList = FXCollections.observableArrayList(list);
		this.appointmentTable.setItems(this.obsList);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		handleSearchAppointment();
	}

}