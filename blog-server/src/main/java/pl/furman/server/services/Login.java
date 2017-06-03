package pl.furman.server.services;

import pl.furman.server.annotations.Action;
import pl.furman.server.annotations.Service;

@Service(name = "blogspace")
public class Login {

	@Action(name = "login")
	public boolean verifyLoginData(String userName, String password){
		//TODO
		System.out.println("User: " + userName + ". Password: " + password);
		
		return false;
	}
}
