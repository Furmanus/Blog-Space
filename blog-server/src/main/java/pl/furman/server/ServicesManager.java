package pl.furman.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import pl.furman.protocol.Message;
import pl.furman.server.annotations.Action;
import pl.furman.server.annotations.Service;
import pl.furman.server.database.DatabaseConnection;
import pl.furman.server.database.JpaDatabaseConnection;

/** 
 * @author £ukasz Lach
 *
 * Singleton class used to store and manage services on server side. In class constructor registry is filled with instances of {@code Service}
 * objects. Also through {@code ServiceManager} services methods can be invoked by using {@code Invoke} method. Both services and services
 * methods are based on annotations ({@code Service} annotations for services and {@code Action} annotations for services methods.
 */
public class ServicesManager {

	private static ServicesManager instance = null;
	private static DatabaseConnection connection = null; //interface use to establish connection to database
	private ConcurrentHashMap<String, Object> registry; //registry where instances of all our services will be held
	
	private ServicesManager(){
		
		registry = new ConcurrentHashMap<>(); 
		connection = new JpaDatabaseConnection();
		
		try{
			
			String path = "pl.furman.server.services"; //name of package where service classes are present
			
			/*
			 * Create new file object which is actually out folder where all Service classes are. Last replace is used in case when user has 
			 * windows OS and its user has name separated by spaces (windows sometimes changes space character to "%20"
			 */
			File directory = new File(ClassLoader.getSystemResource(path.replace('.', '/')).getPath().replaceAll("%20", " "));
	
			for(String item : directory.list()){
				
				Class<?> clazz = Class.forName(path + "." + item.substring(0, item.indexOf('.')));
				
				/*
				 * Check if examined class object has @Service annotation
				 */
				Service service = clazz.getAnnotation(Service.class); 
				
				if(service != null){
					
					registry.put(service.name(), clazz.newInstance()); //if class has Service annotation, we create its instance and put it into registry
				}
			}
		}catch(ClassNotFoundException | InstantiationException | IllegalAccessException e){
			
			e.printStackTrace();
		}
	}
	
	/**
	 * Based on url from {@code Message} this method invokes method from {@code Service} (if that method has annotation {@code Action}
	 * with name equal to second part of url) object from {@code registry} with annotation {@code Service} which name is equal to first part
	 * of {@code Message} url. Method is invoked with parameters equal to parameters from url after "?" character. For example {@code Message}
	 * with url "center/calculate?name=test" will invoke method with annotation {@code Action} which name is equal to "calculate"
	 * from registry with {@code Services} annotation equal to "center". Method will be invoked with parameter "name" equal to "test".
	 * @param msg {@code Message} object send by client.
	 * @return Returns nothing.
	 */
	public void invoke(Message msg, BufferedWriter writer){
		
		try{
		
			Object service = registry.get(msg.getService()); //we get searched service
			
			Class<?> clazz = service.getClass(); //create Class object representing our chosen service class
			
			Method[] methods = clazz.getDeclaredMethods(); //Array of methods declared in service class
			
			for(Method item : methods){
				
				Action action = item.getAnnotation(Action.class); //check if method has Action annotation
				
				if(action != null && action.name().equals(msg.getAction())){
					
					/*
					 * if method has Action annotation and annotation name is equal to name send in Message, invoke method on service object
					 * with parameters from Message url
					 */
					
					msg.getParameters().put("writer", writer);
					item.invoke(service, msg.getParameters().values().toArray());
				}
			}
		}catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
			
			e.printStackTrace();
		}
	}
	
	/**
	 * Method which returns {@code ServicesManager} instance.
	 * @return Returns {@code ServicesManager} instance.
	 */
	public static ServicesManager getInstance(){
		
		if(instance == null){
			
			instance = new ServicesManager();
		}
		
		return instance;
	}
	
	/**
	 * Method which returns {@code DatabaseConnection} object.
	 * @return Returns {@code DatabaseConnection} object.
	 */
	public static DatabaseConnection getConnection(){
		
		return connection;
	}
}
