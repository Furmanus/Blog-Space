package pl.furman.client.gui.controllers;

import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import pl.furman.client.Client;
import pl.furman.protocol.Message;

/**
 * @author £ukasz Lach
 * 
 * Controller class for {@code login.fxml} page which represents entry page (with login and register functions) of application.
 */
public class LoginScreenController {
	
	@FXML
	private TextField userName; //text field where user enters his login
	
	@FXML
	private PasswordField password; //password field where user enters his password
	
	@FXML
	private TextArea messages; //text area field where replies from server can appear
	
	/**
	 * Event handler for "Register" button. TODO
	 */
	@FXML
	private void registerButtonEventHandler(){
		
		Client.getInstance().write("test");
		
		System.out.println(Client.getInstance().read());
	}
	
	/**
	 * Event handler for "Login" button. Method creates new {@code Message} object with {@code service} field set as "blogspace" and {@code action}
	 * field set as "login". Text inside {@code TextField userName} and {@code PasswordField password} are send as parameters under respectively
	 * {@code userName} and {@code password} keys inside {@code HashMap}.
	 */
	@FXML
	private void loginButtonEventHandler(){
		
		Message msg = Message.createNewMessage();
		
		HashMap<String, Object> params = new HashMap<>();
		params.put("password", password.getText());
		params.put("userName", userName.getText());
		msg.setService("blogspace");
		msg.setAction("login");
		msg.setParameters(params);
		msg.setBody(""); //body is empty, we don't want to send any content
		
		Client.getInstance().write(msg.toString()); //send a message
		
		msg.load(Client.getInstance().read()); //receive response
		messages.setText(msg.getBody()); //display response on screen 
		
		if(msg.getBody().equals("login successful")){
			
			//TODO change strings to enums
			Client.getInstance().setSessionId(msg.getParameters().get("sessionId").toString());
			System.out.println("Id sesji: " + Client.getInstance().getSessionId());
		}
	}
	
	public void initialize(){
		
	}
}
