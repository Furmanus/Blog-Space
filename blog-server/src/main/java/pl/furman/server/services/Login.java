package pl.furman.server.services;

import pl.furman.server.ServicesManager;
import pl.furman.server.annotations.Action;
import pl.furman.server.annotations.Service;

@Service(name = "blogspace")
public class Login {

	@Action(name = "login")
	public void verifyLoginData(String password, String userName){
		//TODO return query result to client
		System.out.println("User: " + userName + ". Password: " + password);
		
		System.out.println(ServicesManager.getConnection().verifyUser(userName, password));
	}
}
