package fr.ensibs.common;

import fr.ensibs.userService.ManageUsersServiceImpl;

/**
 * Class that design a customer or an administrator
 * @author Nadjim ABASY and Robin ASPE
 */
public class Person {
	private int id_person;
	private String name_user;
	private String psw;
	private String token;
	private boolean is_admin = false;
	
	/**
	 * Default constructor
	 */
	public Person() {
		
	}
	
	/**
	 * This constructor allow to create a user with an id, a name, a password,
	 * a token for a session to the services and boolean that is true
	 * if the user is an administrator, false if that is a customer.
	 * @param id the unique id for a user 
	 * @param name the name of a user
	 * @param password the password of a user
	 * @param token the token for a session to use the services
	 * @param admin boolean that inform if the user is a customer or an administrator
	 */
	public Person(int id, String name, String password, String token, boolean admin) {
		this.id_person = id;
		this.name_user = name;
		this.psw = password;
		this.token = token;
		this.is_admin = admin;
	}
	
	/**
	 * @return the id_user
	 */
	public int getId_person() {
		return id_person;
	}

	/**
	 * @param id_user the id_user to set
	 */
	/*public void setId_user(int id_user) {
		this.id_user = id_user;
	}*/

	/**
	 * @return the name_user
	 */
	public String getName_user() {
		return name_user;
	}

	/**
	 * @param name_user the name_user to set
	 */
	public void setName_user(String name_user) {
		this.name_user = name_user;
	}

	/**
	 * @return the psw
	 */
	public String getPsw() {
		return psw;
	}

	/**
	 * @param psw the psw to set
	 */
	public void setPsw(String psw) {
		this.psw = psw;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the is_admin
	 */
	public boolean isAdmin() {
		return is_admin;
	}

	/**
	 * @param is_admin the is_admin to set
	 */
	public void setAdmin(boolean is_admin) {
		this.is_admin = is_admin;
	}

	public String toString()
	{
		return "person[" + "name : " + this.name_user + ", psw : " + this.psw + ", token connexion : " + this.token + ", id : " + this.id_person + ", is admin : " + this.is_admin + " ]" ;
	}
}
