package pl.furman.server.session;

import java.io.BufferedWriter;
import java.net.Socket;

public class Session {

	private final String id;
	private BufferedWriter writer;
	private String userName;
	
	public Session(String userName){
		
		this.userName = userName;
		id = md5(String.valueOf((System.currentTimeMillis() + Math.floor(Math.random() * 100000))));
	}
	
	private String md5(String md5) {
		
        try {
        	
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            
            for(int i = 0; i < array.length; ++i) {
            	
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }

            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        	
        	e.printStackTrace();
        }

        return null;
    }
	
	public String getSession(){
		
		return id;
	}
	
	public BufferedWriter getWriter(){
		
		return writer;
	}
}
