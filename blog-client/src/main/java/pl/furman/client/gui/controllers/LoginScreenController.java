package pl.furman.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import pl.furman.client.Client;

public class LoginScreenController {

	@FXML
	private Text welcome;
	
	@FXML
	private void registerButtonHandler(){
		
		Client.getInstance().write("dupa\n");
		System.out.println(Client.getInstance().read());
	}
}
