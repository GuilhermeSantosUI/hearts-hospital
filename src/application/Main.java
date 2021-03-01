package application;

import java.io.IOException;

import gui.MainGUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

	private static Scene mainScene;

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Hearth's Hospital");
		stage.setScene(createScene(loadMainPane()));
		stage.setResizable(false);
		stage.show();
		stage.centerOnScreen();
	}

	private Pane loadMainPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream(VistaNavigator.MAIN));
		MainGUIController mainGUIController = loader.getController();

		VistaNavigator.setMainGUIController(mainGUIController);
		VistaNavigator.loadVista(VistaNavigator.LANDING); 

		return mainPane;
	}

	private Scene createScene(Pane mainPane) {
		Scene scene = new Scene(mainPane);
		return scene;
	}

	public static Scene getMainScene() {
		return mainScene;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
