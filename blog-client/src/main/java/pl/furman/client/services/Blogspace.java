package pl.furman.client.services;

import pl.furman.client.annotations.Action;
import pl.furman.client.annotations.Service;

@Service(name = "blogspace")
public class Blogspace {

	@Action(name = "login")
	public void login(String sessionId){
		//TODO login stuff
		System.out.println("Received sessionId: " + sessionId);
	}
}
