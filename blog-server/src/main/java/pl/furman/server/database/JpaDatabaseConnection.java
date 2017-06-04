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
		em = factory.createEntityManager();
	}

	/**
	 * Method responsible for verifying whether user with certain {@code userName} exists in database and if given {@code password} matches password
	 * for this user.
	 * @param userName {@code String} user name we want to check.
	 * @param password {@code String} password to user account we want to verify.
	 * @return Return {@code true} if given user with password exists in database, returns {@code false} otherwise.
	 */
	@Override
	public boolean verifyUser(String userName, String password) {
		
		try{
			
			em.getTransaction().begin();
			
			Query q = em.createQuery("SELECT c FROM User c WHERE c.userName = :name");
			q.setParameter("name", userName);
			
			User result = (User)q.getSingleResult();
			
			if(result != null && result.getPassword().equals(password)){
				
				return true;
			}else{
				
				return false;
			}
		}catch(NoResultException e){
		
			//TODO send info about no such user
			System.out.println("No such user");
			return false;
		}catch(Throwable e){
			//TODO send info to client about error
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void addUser(String userName, String password) {
		

	}
}
