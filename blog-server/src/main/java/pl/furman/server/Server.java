package pl.furman.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author £ukasz Lach
 *
 * Main class of server side application. Creates new {@code ServerSocket} object which listens to connections at port 30666. In next step
 * {@code Executor} object is created from {@code newFixedThreadPool} static method of {@code Executors} class. Executor will accept up to 30
 * connection simultaneously. With each new connection from client, {@code Socket} object is created and thread with {@code ServerConnection}
 * is started.
 */
public class Server {

	public static void main(String[] args) {
		
		try(ServerSocket serverSocket = new ServerSocket(30666)) {
			
			Executor executor = Executors.newFixedThreadPool(30);
			
			//Server listens for connections. With each new connection, new thread is created with socket passed as argument.
			while(true){
				
				Socket socket = serverSocket.accept();
				executor.execute(new ServerConnection(socket));
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
