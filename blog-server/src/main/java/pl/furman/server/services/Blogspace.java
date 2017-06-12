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

@Service(name = "blogspace")
public class Blogspace {

	@Action(name = "login")
	public void verifyLoginData(Object password, Object writer, Object userName){
		
		try{
			
			String result = ServicesManager.getConnection().verifyUser(userName.toString(), password.toString());
			
			JsonMessage msg = new JsonMessage();
			Session session = new Session(userName.toString());
			
			HashMap<String, Object> params = new HashMap<>();
			params.put("sessionId", session.getSession());
			
			msg.setService("blogspace");
			msg.setAction("login");
			msg.setParameters(params);
			msg.setBody(result);
			
			((BufferedWriter)writer).write(msg.toString() + "\n");
			((BufferedWriter)writer).flush();
		}catch(IOException e){
			
			e.printStackTrace();
		}
	}
}
