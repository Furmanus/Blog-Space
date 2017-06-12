package pl.furman.server.session;

import java.util.ArrayList;

public class SessionManager {

	private static SessionManager instance = null;
	private ArrayList<Session> sessions;
	
	private SessionManager(){
		
		sessions = new ArrayList<>();
	}
	
	public static SessionManager getInstance(){
		
		if(instance == null){
			
			instance = new SessionManager();
		}
		
		return instance;
	}
	
	public ArrayList<Session> getSessions(){
		
		return sessions;
	}
}
