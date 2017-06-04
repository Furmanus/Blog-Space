package pl.furman.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import pl.furman.protocol.JsonMessage;
import pl.furman.protocol.Message;
import pl.furman.server.database.DatabaseConnection;
import pl.furman.server.database.JpaDatabaseConnection;

public class Server {

	public static void main(String[] args) {
		
		try(ServerSocket serverSocket = new ServerSocket(30666)) {
			
			Executor executor = Executors.newFixedThreadPool(30);
			
			while(true){
				
				Socket socket = serverSocket.accept();
				executor.execute(new ServerConnection(socket));
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
