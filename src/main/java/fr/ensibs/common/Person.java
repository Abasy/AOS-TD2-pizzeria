package fr.ensibs.common;

import javax.xml.bind.annotation.XmlElement;

/**
 * Class that design a customer or an administrator
 * @author Nadjim ABASY and Robin ASPE
 */
public class Person {
	
	public enum PERMISSION {NONE, USER, ROOT};
	
	@XmlElement(name = "id")
	private int id_person;	
	private String name_user;
	private String psw;
	private String token;
	@XmlElement(name="permission")
	private PERMISSION permission = PERMISSION.NONE;
	
	/**
	 * Default constructor
	 */
	public Person() {
		
	}
	
	/**
	 * This constructor allow to create a user with an id, a name, a password,
	 * a token for a session to the services and boolean that is true
	 * if the user is an administrator, false if that is a customer.
	 * @param id the unique id for the user 
	 * @param name the name of a user, must be unique
	 * @param password the password of a user
	 * @param token the token for a session to use the services
	 * @param permission Inform if the user is a customer or an administrator
	 */
	public Person(int id, String name, String password, String token, PERMISSION permission) {
		this.id_person = id;
		this.name_user = name;
		this.psw = password;
		this.token = token;
		this.permission = permission;
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
	public void setId_user(int id_user) {
		this.id_person = id_user;
	}

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
	 * @return the permission level
	 */
	public PERMISSION permission() {
		return permission;
	}

	/**
	 * @param permission the permission to set
	 */
	public void setPermission(PERMISSION permission) {
		this.permission = permission;
	}

	/**
	 * @return true if the user is an admin
	 */
	public boolean isAdmin() {
		return (permission == PERMISSION.ROOT);
	}
	
	/**
	 * @return true if the user is a patron
	 */
	public boolean isUser() {
		return (permission== PERMISSION.USER);
	}

	
	public String toString()
	{
		return "person[" + "name : " + this.name_user + ", psw : " + this.psw + ", token connexion : " + this.token + ", id : " + this.id_person + ", is admin : " + this.permission + " ]" ;
	}
}
