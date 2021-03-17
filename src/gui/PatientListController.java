package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.VistaNavigator;
import gui.listener.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
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
import model.entities.Patient;
import model.services.PatientService;

public class PatientListController implements Initializable, DataChangeListener {

	@FXML
	private VBox patientContainer;

	@FXML
	private TableView<Patient> patientTable;

	@FXML
	private TableColumn<Patient, String> nameColumn;

	@FXML
	private TableColumn<Patient, String> genderColumn;

	@FXML
	private TableColumn<Patient, Date> birthColumn;

	@FXML
	private TableColumn<Patient, String> addressColumn;

	@FXML
	private TableColumn<Patient, String> telephoneColumn;

	@FXML
	private TableColumn<Patient, String> callColumn;

	@FXML
	private TableColumn<Patient, String> emailColumn;

	@FXML
	private TextField txtSearch;

	private ObservableList<Patient> obsList;

	private PatientService service = new PatientService();

	@FXML
	void goBack() {
		VistaNavigator.loadVista(VistaNavigator.DASHBOARD);
	}

	@FXML
	private void handleOpenModal(MouseEvent event) {
		Dialog<ButtonType> dialog = new Dialog<>();
		Window window = dialog.getDialogPane().getScene().getWindow();
		window.setOnCloseRequest(e -> window.hide());
		dialog.initOwner(patientContainer.getScene().getWindow());
		dialog.setTitle("New patient");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("PatientForm.fxml"));
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
		if (patientTable.getSelectionModel().getSelectedItem() == null) {
			Alerts.showAlert("Erro ao editar a consulta!", null, "Selecione a consulta na tabela para poder editar!",
					AlertType.ERROR);
		} else if (patientTable.getSelectionModel().getSelectedItem() != null) {
			Dialog<ButtonType> dialog = new Dialog<>();
			Window window = dialog.getDialogPane().getScene().getWindow();
			window.setOnCloseRequest(e -> window.hide());
			dialog.initOwner(patientContainer.getScene().getWindow());
			dialog.setTitle("Edit patient");
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PatientForm.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			PatientFormController controller = fxmlLoader.getController();
			controller.setPatient(patientTable.getSelectionModel().getSelectedItem());
			controller.setServices(new PatientService());
			controller.handleUpdateData();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();
			newStage.setScene(newScene);
			newStage.show();
			VistaNavigator.loadVista(VistaNavigator.DOCTORLIST);
		}
	}

	private void handleSearchPatient() {
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
		genderColumn.setCellValueFactory(new PropertyValueFactory<>("sexo"));
		birthColumn.setCellValueFactory(new PropertyValueFactory<>("datanascimento"));
		Utils.formatTableColumnDate(birthColumn, "dd/MM/yyyy");
		addressColumn.setCellValueFactory(new PropertyValueFactory<>("endereco"));
		telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telefone"));
		callColumn.setCellValueFactory(new PropertyValueFactory<>("numcelular"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		List<Patient> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		FilteredList<Patient> filteredData = new FilteredList<>(obsList, b -> true);
		txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(patient -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (patient.getNome().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches username
				} else {
					return false; // Does not match.
				}

			});
		});
		SortedList<Patient> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(patientTable.comparatorProperty());
		patientTable.setItems(sortedData);
	}

	@FXML
	private void handleRemoveSelectedValue(MouseEvent event) {
		if (patientTable.getSelectionModel().getSelectedItem() == null) {
			Alerts.showAlert("Erro ao remover a paciente!", null, "Selecione o paciente na tabela para poder deletar!",
					AlertType.ERROR);
		} else if (patientTable.getSelectionModel().getSelectedItem() != null) {
			Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
			try {
				service.remove(selectedPatient);
				Alerts.showAlert("Medico removida com sucesso!", null, "O medico foi removida da tabela!",
						AlertType.CONFIRMATION);
				updateTableView();
			} catch (Exception e) {
				Alerts.showAlert("Tivemos algum problema!", "Parece que aconteceu algo inesperado no sistema!",
						"Tente novamente mais tarde!", AlertType.ERROR);
			}

		}
	}

	private void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Patient> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		patientTable.setItems(obsList);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		handleSearchPatient();
	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}

}
