package pl.furman.server.services;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import pl.furman.protocol.JsonMessage;
import pl.furman.server.ServicesManager;
import pl.furman.server.annotations.Action;
import pl.furman.server.annotations.Service;
import pl.furman.server.session.Session;
import pl.furman.server.session.SessionManager;

/**
 * @author £ukasz Lach
 * 
 * Main service class responsible for managing blog space from server side, mostly retrieving and editing data in database.
 */
@Service(name = "blogspace")
public class Blogspace {

	/**
	 * Method used to verify whether user with certain password is already registered in database. It uses method {@code verifyUser} from 
	 * {@code DatabaseConnection} object. Sends {@code Message} object back to client with result as a {@code String} in message body.
	 * {@code Message} url is equal to "blogspace/login" with optional parameters (Id of {@code Session} object, in case if login was successful).
	 * Creates new {@code Session} object in case of successful login.
	 * @param password {@code String} password for certain user in database.
	 * @param writer {@code BufferedWriter} object used to writer response back to client. {@code BufferedWriter} object has to contain underlying
	 * {@code Socket} object.
	 * @param userName {@code String} name of user in database we want to verify.
	 */
	@Action(name = "login")
	public void verifyLoginData(Object password, Object writer, Object userName){
		
		try{
			
			//verify whether given user with password exists in database
			String result = ServicesManager.getConnection().verifyUser(userName.toString(), password.toString());
			
			JsonMessage msg = new JsonMessage();
			HashMap<String, Object> params = new HashMap<>();
			
			//if login is successful create new Session and store it in SessionManager
			if(result.equals("login successful")){
				
				Session session = new Session(userName.toString(), (BufferedWriter)writer);
				SessionManager.getInstance().getSessions().put(session.getSession(), session);
				params.put("sessionId", session.getSession()); //store Session unique Id in message parameters
			}
			
			msg.setService("blogspace");
			msg.setAction("login");
			msg.setParameters(params);
			msg.setBody(result); //result of verification is send in message body
			
			((BufferedWriter)writer).write(msg.toString() + "\n");
			((BufferedWriter)writer).flush(); //send message
		}catch(IOException e){
			
			e.printStackTrace();
		}
	}
}
