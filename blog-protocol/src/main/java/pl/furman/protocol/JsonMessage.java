package pl.furman.protocol;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

public class JsonMessage extends Message {
	
	public JsonMessage(){
		
		super();
	}

	public JsonMessage(String service, String action, HashMap<String, String> parameters, String body){
		
		this.service = service;
		this.action = action;
		this.parameters = parameters;
		this.body = body;
	}
	
	@Override
	public void load(String src) {
		
		JsonReader reader = Json.createReader(new StringReader(src));
		
		JsonObject object = reader.readObject();
		
		this.url = object.getString("url");
		this.body = object.getString("body");
		
		int mark = url.indexOf('?');
		
		this.service = url.substring(0, url.indexOf('/'));
		this.action = (mark != -1) ? url.substring(url.indexOf('/')+1, mark) : url.substring(url.indexOf('/')+1);
		
		if(mark != -1){
			
			int start = -1;
			int equalityMark = -1;
			int end = -1;
			
			while(mark < url.length()){
				
				if(url.charAt(mark) == '?'){
					
					start = mark + 1;
				}
				
				if(url.charAt(mark) == '='){
					
					equalityMark = mark;
				}else if(url.charAt(mark) == '&'){
					
					end = mark;
					
					this.parameters.put(url.substring(start, equalityMark), url.substring(equalityMark + 1, end));
					
					start = mark + 1;
				}
				
				mark++;
			}
			
			this.parameters.put(url.substring(start, equalityMark), url.substring(equalityMark + 1));
		}
	}

	@Override
	public String toString() {
		
		String url = this.service + "/" + this.action + "?";
		Set<String> keys = parameters.keySet();
		
		for(String item : keys){
			
			url += item + "=" + parameters.get(item) + "&";
		}
		
		url = url.substring(0, url.length() - 1);
		
		JsonObjectBuilder builder = Json.createObjectBuilder();
		
		builder.add("url", url);
		builder.add("body", body);
		
		return builder.build().toString();
	}
}
