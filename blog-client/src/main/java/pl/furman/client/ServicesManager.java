package pl.furman.client;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import pl.furman.client.annotations.Action;
import pl.furman.client.annotations.Service;
import pl.furman.protocol.Message;

public class ServicesManager {

	private static ServicesManager instance = null;
	private ConcurrentHashMap<String, Object> registry;
	
	private ServicesManager(){
		
		registry = new ConcurrentHashMap<>();
		
		try{
			
			String path = "pl.furman.client.services";
			File directory = new File(ClassLoader.getSystemResource(path.replace('.', '/')).getPath().replaceAll("%20", " "));
	
			for(String item : directory.list()){
				
				Class<?> clazz = Class.forName(path + "." + item.substring(0, item.indexOf('.')));
				
				Service service = clazz.getAnnotation(Service.class);
				
				if(service != null){
					
					registry.put(service.name(), clazz.newInstance());
				}
			}
		}catch(ClassNotFoundException | InstantiationException | IllegalAccessException e){
			
			e.printStackTrace();
		}
	}
	
	public void invoke(Message msg){
		
		try{
		
			Object service = registry.get(msg.getService());
			
			Class<?> clazz = service.getClass();
			
			Method[] methods = clazz.getDeclaredMethods();
			
			for(Method item : methods){
				
				Action action = item.getAnnotation(Action.class);
				
				if(action != null){
					
					item.invoke(service, msg.getParameters().values().toArray());
				}
			}
		}catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
			
			e.printStackTrace();
		}
	}
	
	public static ServicesManager getInstance(){
		
		if(instance == null){
			
			instance = new ServicesManager();
		}
		
		return instance;
	}
}
