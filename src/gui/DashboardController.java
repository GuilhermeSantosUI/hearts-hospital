package gui;

import application.VistaNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DashboardController {
	
	@FXML
	private Button exitButton;
	
	
	@FXML
	public void handleExitAcount() {
		VistaNavigator.loadVista(VistaNavigator.LANDING);
	}

}
