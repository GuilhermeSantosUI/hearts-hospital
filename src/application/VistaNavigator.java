package application;

import java.io.IOException;

import gui.MainGUIController;
import javafx.fxml.FXMLLoader;

public class VistaNavigator {

	public static final String MAIN = "../gui/main.fxml";
	public static final String LANDING = "../gui/landingPage.fxml";
	public static final String DASHBOARD = "../gui/dashboard.fxml";
	public static final String DOCTORLIST = "../gui/DoctorList.fxml";
	public static final String APPOINTMENTLIST = "../gui/AppointmentList.fxml";
	public static final String PATIENTLIST = "../gui/PatientList.fxml";

	private static MainGUIController mainGUIController;

	public static void setMainGUIController(MainGUIController mainGUIController) {
		VistaNavigator.mainGUIController = mainGUIController;
	}

	public static void loadVista(String fxml) {
		try {
			mainGUIController.setVista(FXMLLoader.load(VistaNavigator.class.getResource(fxml)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}