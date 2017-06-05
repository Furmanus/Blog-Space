package pl.furman.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import pl.furman.protocol.Message;

public class ServerConnection implements Runnable {
	
	private Socket socket;
	private BufferedReader reader;
	private BufferedWriter writer;
	
	public ServerConnection(Socket socket){
		
		this.socket = socket;
		try {
			
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		
		try {
			
			while(true){
				
				String receivedText = reader.readLine();

				Message msg = Message.createNewMessage();
				msg.load(receivedText);
				
				ServicesManager.getInstance().invoke(msg);
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
