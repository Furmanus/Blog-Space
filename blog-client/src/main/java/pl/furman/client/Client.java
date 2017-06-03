package pl.furman.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	private Socket socket;
	private BufferedReader reader;
	private BufferedWriter writer;
	private static Client instance = null;
	
	private Client(){
		
		try {
			socket = new Socket("localhost", 30666);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public static Client getInstance(){
		
		if(instance == null){
			
			instance = new Client();
		}
		
		return instance;
	}
	
	public void write(String text){
		
		try {
			
			writer.write(text + "/n");
			writer.flush();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public String read(){
		
		String result;
		try {
			
			result = reader.readLine();
			return result;
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return "error";
	}
}
