package pl.furman.server.database;

/**
 * @author £ukasz Lach
 * 
 * Class responsible for connecting to database via Eclipselink and JPA.
 */
public interface DatabaseConnection {

	/**
	 * Method responsible for verifying whether user with certain {@code userName} exists in database and if given {@code password} matches password
	 * for this user.
	 * @param userName {@code String} user name we want to check.
	 * @param password {@code String} password to user account we want to verify.
	 * @return Return {@code true} if given user with password exists in database, returns {@code false} otherwise.
	 */
	public boolean verifyUser(String userName, String password);
	
	
	public void addUser(String userName, String password);
}
