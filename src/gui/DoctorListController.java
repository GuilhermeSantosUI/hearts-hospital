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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import model.entities.Doctor;
import model.services.DoctorService;

public class DoctorListController implements Initializable, DataChangeListener {

	@FXML
	private VBox doctorsContainer;

	@FXML
	private TableView<Doctor> doctorTable;

	@FXML
	private TableColumn<Doctor, Integer> crmColumn;

	@FXML
	private TableColumn<Doctor, String> nameColumn;

	@FXML
	private TableColumn<Doctor, String> cpfColumn;

	@FXML
	private TableColumn<Doctor, String> emailColumn;

	@FXML
	private TableColumn<Doctor, String> cellColumn;

	@FXML
	private TableColumn<Doctor, Date> birthColumn;

	@FXML
	private TableColumn<Doctor, String> passColumn;

	@FXML
	private TextField txtSearch;

	private ObservableList<Doctor> obsList;

	private DoctorService service = new DoctorService();

	@FXML
	void goBack() {
		VistaNavigator.loadVista(VistaNavigator.DASHBOARD);
	}

	@FXML
	private void handleOpenModal(MouseEvent event) {
		Dialog<ButtonType> dialog = new Dialog<>();
		Window window = dialog.getDialogPane().getScene().getWindow();
		window.setOnCloseRequest(e -> window.hide());
		dialog.initOwner(doctorsContainer.getScene().getWindow());
		dialog.setTitle("New doctor");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("DoctorForm.fxml"));
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

	private void handleSearchPatient() {
		crmColumn.setCellValueFactory(new PropertyValueFactory<>("crm"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("nomemed"));
		cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("emailmed"));
		cellColumn.setCellValueFactory(new PropertyValueFactory<>("numcelularmed"));
		birthColumn.setCellValueFactory(new PropertyValueFactory<>("datanascimentomed"));
		Utils.formatTableColumnDate(birthColumn, "dd/MM/yyyy");
		passColumn.setCellValueFactory(new PropertyValueFactory<>("senha"));
		List<Doctor> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		FilteredList<Doctor> filteredData = new FilteredList<>(obsList, b -> true);
		txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(patient -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (patient.getNomemed().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches username
				} else {
					return false; // Does not match.
				}

			});
		});
		SortedList<Doctor> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(doctorTable.comparatorProperty());
		doctorTable.setItems(sortedData);
	}

	@FXML
	private void handleRemoveSelectedValue(MouseEvent event) {
		if (doctorTable.getSelectionModel().getSelectedItem() == null) {
			Alerts.showAlert("Erro ao remover a consulta!", null, "Selecione a consulta na tabela para poder deletar!",
					AlertType.ERROR);
		} else if (doctorTable.getSelectionModel().getSelectedItem() != null) {
			Doctor selectedDoctor = doctorTable.getSelectionModel().getSelectedItem();
			try {
				service.remove(selectedDoctor);
				Alerts.showAlert("Consulta removida com sucesso!", null, "A consulta foi removida da tabela!",
						AlertType.ERROR);
			} catch (Exception e) {
				Alerts.showAlert("O medico não pode ser removido!",
						"Existe uma relação com esse medico em outra pagina",
						"Por conta de uma relação o medico não pode ser removido!", AlertType.ERROR);
			}
		}
	}

	@FXML
	private void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Doctor> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		doctorTable.setItems(obsList);
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
