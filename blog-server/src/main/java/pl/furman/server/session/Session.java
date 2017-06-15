package pl.furman.server.session;

import java.io.BufferedWriter;
import java.net.Socket;

/**
 * @author £ukasz Lach
 *
 * Class representing a session. It contains unique Id to identify client.
 */
public class Session {

	private final String id; //unique Id to identify client
	private BufferedWriter writer; //BufferedReader which contains underlying Socket connected to client.
	private String userName; //name of connected user
	
	/**
	 * Public constructor for {@code Session} object.
	 * @param userName {@code String} name of user.
	 * @param writer {@code BufferedWriter} object used to send messages to client. Contains underlying {@code Socket} object connected to client.
	 */
	public Session(String userName, BufferedWriter writer){
		
		this.writer = writer;
		this.userName = userName;
		this.id = md5(String.valueOf((System.currentTimeMillis() + Math.floor(Math.random() * 100000)))); //unique encrypted Id
	}
	
	/**
	 * Creates encrypted text with {@code md5}.
	 * @param md5 {@code String} text which has to be encrypted.
	 * @return Returns {@code String} encrypted text.
	 */
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
	
	/**
	 * 
	 * @return Returns {@code Session} unique Id.
	 */
	public String getSession(){
		
		return id;
	}
	
	/**
	 * 
	 * @return Returns {@code BufferedWriter} object connected to client.
	 */
	public BufferedWriter getWriter(){
		
		return writer;
	}
}
