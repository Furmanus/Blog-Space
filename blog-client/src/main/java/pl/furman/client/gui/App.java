package pl.furman.client.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author £ukasz Lach
 *
 * Main class for Client graphical interface.
 */
public class App extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("/META-INF/FXML/login.fxml")); //load fxml file with login screen
		
		Scene scene = new Scene(root, 640, 480);
		
		stage.setTitle("Blog space");
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		
		launch(args);
	}
}
