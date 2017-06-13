package pl.furman.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import pl.furman.protocol.JsonMessage;
import pl.furman.protocol.Message;

/**
 * @author £ukasz Lach
 *
 * Class responsible for running and maintaining connection with client.
 */
public class ServerConnection implements Runnable {
	
	private Socket socket; //socket received from Server class
	private BufferedReader reader; //reader used to receive data from client
	private BufferedWriter writer; //writer used to send data to client
	
	/**
	 * Public constructor. Sets private {@code Socket} field with socket received as parameter and creates {@code BufferedReader} and
	 * {@code BufferedWriter} objects used to receive from and send data to client.
	 * @param socket {@code Socket} object received from {@code Server} object.
	 */
	public ServerConnection(Socket socket){
		
		this.socket = socket;
		try {
			
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	/**
	 * Creates main infinite loop of individual server connection. Method listens for messages from client. When message is received, method
	 * creates {@code JsonMessage} object and passes it along with {@code BufferedWriter} writer object to {@code ServicesManager invoke} method.
	 * Method {@code invoke} triggers certain method from services (based on message url, only methods with services and methods with proper
	 * annotations are triggered. For example message with url "blogspace/login?name=heniu&password=1234" will execute method
	 * with annotation @Action which name is equal to "login" from Service class with annotation @Service which name equal to "blogspace". 
	 * Method will be executed with parameters "name" equal to "heniu" and "password" equal to "1234".
	 */
	@Override
	public void run() {
		
		try {
			
			while(true){
				
				String receivedText = reader.readLine(); //listen for message from client

				Message msg = JsonMessage.createNewMessage(); //creates new empty JsonMessage object
				msg.load(receivedText); //fills empty JsonMessage object with data from received text message
				
				ServicesManager.getInstance().invoke(msg, writer); //Invoke method from ServicesManager. Method choice is based on message url.
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
