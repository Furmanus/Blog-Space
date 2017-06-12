package pl.furman.server.database;

import javax.activity.InvalidActivityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import pl.furman.server.database.entities.User;

/**
 * @author £ukasz Lach
 * 
 * Class responsible for connecting to database via Eclipselink and JPA.
 */
public class JpaDatabaseConnection implements DatabaseConnection {
	
	private EntityManagerFactory factory;
	private EntityManager em;
	
	/**
	 * Public constructor of {@code JpaDatabaseConnection} object. Creates {@code EntityManagerFactory factory} object and {@code EntityManager em}.
	 */
	public JpaDatabaseConnection() {
		
		factory = Persistence.createEntityManagerFactory("blogspace");
	}

	/**
	 * Method responsible for verifying whether user with certain {@code userName} exists in database and if given {@code password} matches password
	 * for this user.
	 * @param userName {@code String} user name we want to check.
	 * @param password {@code String} password to user account we want to verify.
	 * @return Return {@code true} if given user with password exists in database, returns {@code false} otherwise.
	 */
	@Override
	public String verifyUser(String userName, String password) {
		
		try{
			
			em = factory.createEntityManager();
			
			em.getTransaction().begin();
			
			Query q = em.createQuery("SELECT c FROM User c WHERE c.userName = :name");
			q.setParameter("name", userName);
			
			User result = (User)q.getSingleResult();
			
			em.close();
			
			if(result != null && result.getPassword().equals(password)){
				
				return "login successful";
			}else{
				
				return "wrong password";
			}
		}catch(NoResultException e){
		
			return "no such user";
		}catch(Throwable e){
			//TODO send info to client about error
			e.printStackTrace();
			return "error with connection to database";
		}
	}

	@Override
	public void addUser(String userName, String password) {
		

	}
}
