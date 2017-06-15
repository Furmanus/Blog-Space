package pl.furman.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author £ukasz Lach
 * 
 * Class responsible for maintaining client-side logic. It implements singleton design pattern.
 */
public class Client {
	
	private Socket socket; //socket to communicate with server
	private BufferedReader reader; //reader to read data from server
	private BufferedWriter writer; //writer to write data to server
	private static Client instance = null;
	private String sessionId; //session Id. Created after connecting with server for first time.
	
	/**
	 * Private constructor. Creates new Socket, BufferedReader(from socket input stream) and BufferedWriter(from socket output stream).
	 */
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
	
	/**
	 * 
	 * @return Returns only instance of {@code Client}.
	 */
	public static Client getInstance(){
		
		if(instance == null){
			
			instance = new Client();
		}
		
		return instance;
	}
	
	/**
	 * Sends {@code String} text data to server.
	 * @param text {@code String} data send to client.
	 */
	public void write(String text){
		
		try {
			
			writer.write(text + "\n"); //writer data to writer
			writer.flush(); //flush written data to server
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads text data from {@code BufferedReader} object which reads data from underlying {@code Socket} object;
	 * @return Returns {@code String} data text from {@code BufferedReader} object.
	 */
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

	/**
	 * 
	 * @return Returns unique Id of {@code Session} object.
	 */
	public String getSessionId() {
		
		return sessionId;
	}

	/**
	 * Sets unique Id of {@code Session} object.
	 * @param sessionId {@code String} unique Id of session.
	 */
	public void setSessionId(String sessionId) {
		
		this.sessionId = sessionId;
	}
}
