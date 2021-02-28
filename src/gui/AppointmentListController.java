package gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public class AppointmentListController {
	
	@FXML
	private VBox appointmentContainer;

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
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		dialog.showAndWait();
	}

}
