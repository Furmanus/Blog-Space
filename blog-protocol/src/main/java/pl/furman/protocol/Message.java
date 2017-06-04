package pl.furman.protocol;

import java.util.HashMap;
import java.util.Map;

public abstract class Message{

	protected String service;
	protected String action;
	protected String url;
	protected HashMap<String, String> parameters;
	protected String body;
	
	public Message(){
		
		service = "";
		action = "";
		url = "";
		parameters = new HashMap<>();
		body = "";
	}
	
	public abstract void load(String src);
	
	@Override
	public abstract String toString();

	public String getService() {
		
		return service;
	}

	public void setService(String service) {
		
		this.service = service;
	}
	
	public String getAction(){
		
		return action;
	}
	
	public void setAction(String action){
		
		this.action = action;
	}

	public HashMap<String, String> getParameters() {
		
		return parameters;
	}

	public void setParameters(HashMap<String, String> parameters) {
		
		this.parameters = parameters;
	}
	
	public String getBody(){
		
		return body;
	}
	
	public void setBody(String body){
		
		this.body = body;
	}
	
	public static Message createNewMessage(){
		
		return new JsonMessage();
	}
}
