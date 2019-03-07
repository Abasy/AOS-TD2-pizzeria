package fr.ensibs.userService;

import java.security.SecureRandom;
import java.util.ArrayList;

import javax.jws.WebParam;
import javax.jws.WebService;

import fr.ensibs.common.InvalidTokenException;
import fr.ensibs.common.NoPermissionException;
import fr.ensibs.common.Person;
import fr.ensibs.common.Person.PERMISSION;

/**
 * Manage all the users. Each user can sign up, sign in and sign out.
 * But only the administrator can delete a customer and get customer
 * by its id
 * @author Nadjim ABASY and Robin ASPE
 */
@WebService(endpointInterface="fr.ensibs.userService.ManageUsersServiceImpl", serviceName="ManageUsersService", portName="ManageUsersServicePort")
public class ManageUsersServiceImpl implements ManageUsersService {
	
	private int last_id;
	private ArrayList<Integer> free_id;
	private ArrayList<Person> persons;
	
	public ManageUsersServiceImpl() {
		this.last_id = 0;
		this.free_id = new ArrayList<Integer>();
		this.persons = new ArrayList<Person>();
	}
	
	/**
	 * Sign in a customer or an administrator to the pizzeria services
	 * @param name the password of the user
	 * @param psw the password of the user
	 * @return A token to identify a customer or an administrator
	 */
	public String signIn(@WebParam(name = "name") String name, @WebParam(name = "password") String psw) {
		for (Person check_bdd_person : this.persons) {
			if (check_bdd_person.getName_user().equals(name) == true && check_bdd_person.getPsw().equals(psw) == true) {
				if(check_bdd_person.getToken().equals("")) {
					byte[] array = new byte[20] ;
				    new SecureRandom().nextBytes( array ) ;
				    String token =  array.toString().substring(3, array.toString().length());
				    check_bdd_person.setToken(token);
					if (check_bdd_person.isAdmin() == true) {
						return "Successful login, a new token has been generated : " + check_bdd_person.getToken();
					}
					return "Successful login, your token to use to make an order is : " + check_bdd_person.getToken();
				}
				return "You are already logged";
			}
		}
		return "Incorrect name or password." ;
	}

	/**
	 * Sign out a customer or an administrator
	 * @param token the token used during a session
	 * @return message information about if the logout was done successfully
	 * @throws InvalidTokenException 
	 */
	public String signOut(String token) throws InvalidTokenException {
		for (Person check_bdd_person : this.persons) {
			if (check_bdd_person.getToken().equals(token) == true) 
			{
				check_bdd_person.setToken("");
				return "Successful logout, we hope to see you again soon " + check_bdd_person.getName_user() + ". " ;
			}
		}
		throw new InvalidTokenException("Invalid token " + token + "trying to sign out.") ;
	}

	/**
	 * Allow users registration
	 * @param name the name of the user
	 * @param psw the password of the user
	 * @param psw_verification the password of the user
	 * @param isAdmin check if the user is admin or customer
	 * @return Message information about if the subscription was successfully done
	 */
	public String signUp(@WebParam(name = "name") String name, @WebParam(name = "password") String psw, @WebParam(name = "password_verification") String psw_verification, @WebParam(name = "Permission level") Person.PERMISSION permission) {
		for( Person check_bdd_person : this.persons ) {
			if (check_bdd_person.getName_user().equals(name) == true ) {
				return "This username already exists." ;
			}
		}
		
		if (psw.equals(psw_verification) == true ) {
			if ( name.length() > 1 ) {
				if ( name.length() <= 16 ) {
					if ( permission != Person.PERMISSION.USER && permission != Person.PERMISSION.ROOT )
					{
						Person new_person = new Person( this.next_id_user(), name, psw, "", permission);
						System.out.println("new id_person signed up => "+new_person.getId_person());
						this.persons.add(new_person) ;
						return "Inscription success." ;
					}
					return "User permission not recognised";
				}
				return "The entered name is too long (more than 16 characters)." ;
			}
			return "The name entered is too short (less than 2 characters)." ;
		}
		return "Passwords are not identical." ;
	}

	/**
	 * Allow to display all users subscribed to the services
	 * @param token a validate token of an existing administrator
	 * @return the list of all users
	 * @throws NoPermissionException 
	 */
	public ArrayList<Person> getPersons(String token) throws NoPermissionException {
		if (this.getTokenPermission(token) == PERMISSION.ROOT)
			{
				return this.persons;
			}
		else
			throw new NoPermissionException(PERMISSION.ROOT);
	}


	public Person getPersonByID(@WebParam(name = "id_user") int id, String token) throws NoPermissionException {
		if (this.getTokenPermission(token) == PERMISSION.ROOT)
		{
			for(Person pers : this.persons){
				if (pers.getId_person() == id)
				{
					return pers;
				}
			}
			return null;
		}
		else 
			throw new NoPermissionException(PERMISSION.ROOT);
	}

	public Person getPersonByToken(@WebParam(name = "token_user")String token) {
		if (token == "")
		{
			return null;
		}
		for(Person pers : this.persons){
			if (pers.getToken() == token)
			{
				return pers;
			}
		}
		return null;
	}
	
	/**
	 * Method to delete user by permission of an administrator
	 * @param id the id of the user to delete
	 * @param token a validate token of an an existing administrator
	 * @return message information if the user is deleted correctly
	 * @throws NoPermissionException 
	 */
	public String deleteUser(@WebParam(name = "id_user") int id, String token) throws NoPermissionException {
		if (this.getTokenPermission(token) == PERMISSION.ROOT)
		{
			for(Person pers : this.persons){
				if (pers.getId_person() == id)
				{
					this.persons.remove(pers);
					this.free_id.add(id);
				}
			}
			return null;
		}
		else 
			throw new NoPermissionException(PERMISSION.ROOT);
	}
	
	/**
	 * Method to get the permission equivalent of a token. SECURICY ALERT: CAN BE USED TO TEST BRUTE-FORCE CREATED TOKENS
	 * @param token The token to convert in a permission equivalent
	 * @return the permission of the token, or PERMISSION.NONE if the token doesn't exists
	 */
	public PERMISSION getTokenPermission(String token) {
		Person p = this.getPersonByToken(token);
		if (p == null)
			return PERMISSION.NONE;
		else
			return p.permission();
};
	
	
	/**
	 * Private method getting the next free ID for a new user.
	 * @return A new ID that can be used to sign in a new user
	 */
	private int next_id_user() {
		
		int result = 0;
		
		if (this.free_id.isEmpty())
		{
			result = this.last_id;
			last_id++;
		}
		else
		{
			result = this.free_id.remove(0);
		}
		
		return result;
	}


	
}
