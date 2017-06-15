package pl.furman.server.session;

import java.util.HashMap;

public class SessionManager {

	private static SessionManager instance = null;
	private HashMap<String, Session> sessions;
	
	private SessionManager(){
		
		sessions = new HashMap<>();
	}
	
	public static SessionManager getInstance(){
		
		if(instance == null){
			
			instance = new SessionManager();
		}
		
		return instance;
	}
	
	public HashMap<String, Session> getSessions(){
		
		return sessions;
	}
}
