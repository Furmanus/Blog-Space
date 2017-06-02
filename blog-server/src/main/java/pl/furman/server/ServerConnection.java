package pl.furman.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

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
				
				String text = reader.readLine();
				
				System.out.println(text);
				
				Double roll = Math.floor(Math.random() * 100);
				
				writer.write(roll.toString() + "\n");
				writer.flush();
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
