package pl.furman.server.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "username")
	private String userName;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "joindate")
	private String joinDate;

	public String getUserName() {
		
		return userName;
	}

	public void setUserName(String userName) {
		
		this.userName = userName;
	}

	public String getPassword() {
		
		return password;
	}

	public void setPassword(String password) {
		
		this.password = password;
	}

	public String getJoinDate() {
		
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		
		this.joinDate = joinDate;
	}
}
