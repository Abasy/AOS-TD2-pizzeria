package fr.ensibs.userService;

import java.security.SecureRandom;
import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import fr.ensibs.common.BadValueException;
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
	
	/**
	 * Constructor
	 */
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
	 * @throws Exception if already logged
	 * @throws BadValueException if user or password is wrong
	 */
	@WebMethod(exclude = false)
	public String signIn(@WebParam(name = "name") String name, @WebParam(name = "password") String psw) throws Exception, BadValueException {
		for (Person check_bdd_person : this.persons) {
			if (check_bdd_person.getName_user().equals(name) == true && check_bdd_person.getPsw().equals(psw) == true) {
				if(check_bdd_person.getToken().equals("")) {
					byte[] array = new byte[20] ;
				    new SecureRandom().nextBytes( array ) ;
				    String token =  array.toString().substring(3, array.toString().length());
				    check_bdd_person.setToken(token);
				    return token;
				}
				throw new Exception("You are already logged");
			}
		}
		throw new BadValueException("Wrong username/Password");
	}

	/**
	 * Sign out a customer or an administrator
	 * @param token the token used during a session
	 * @return message information about if the logout was done successfully
	 * @throws InvalidTokenException If token given is empty or doesn't exists in database
	 */
	@WebMethod(exclude = false)
	public void signOut(@WebParam(name = "token") String token) throws InvalidTokenException {
		if (token == null)
			throw new InvalidTokenException("Invalid token " + token + "trying to sign out.");
		boolean flag = true;
		for (Person check_bdd_person : this.persons) {
			if (check_bdd_person.getToken().equals(token) == true) 
			{
				check_bdd_person.setToken("");
				flag = false;
			}
		}
		if (flag)
			throw new InvalidTokenException("Invalid token " + token + " trying to sign out.") ;
	}

	/**
	 * Allow users registration
	 * @param name the name of the user
	 * @param psw the password of the user
	 * @param psw_verification the password of the user
	 * @param permission the permission level of this new user
	 * @return the newly created person
	 * @throws BadValueException if a value is not accepted
	 */
	@WebMethod(exclude = false)
	public Person signUp(@WebParam(name = "name") String name, @WebParam(name = "password") String psw, @WebParam(name = "password_verification") String psw_verification, @WebParam(name = "Permission_level") Person.PERMISSION permission) throws BadValueException {
		for( Person check_bdd_person : this.persons ) {
			if (check_bdd_person.getName_user().equals(name) == true ) {
				throw new BadValueException("Username already exists. Please choose another.");
			}
		}
		
		if (psw.equals(psw_verification) == true ) {
			if ( name.length() > 1 ) {
				if ( name.length() <= 16 ) {
					if ( permission == Person.PERMISSION.USER || permission == Person.PERMISSION.ROOT )
					{
						Person new_person = new Person( this.next_id_user(), name, psw, "", permission);
						System.out.println("new id_person signed up => "+new_person.getId_person());
						this.persons.add(new_person) ;
						return new_person ;
					}
					throw new BadValueException("permission should be USER or ROOT.");
				}
			}
			throw new BadValueException("Username must be between 2 and 16 characters long.");
		}
		throw new BadValueException("Password and verification don't match.");
	}

	/**
	 * Allow to display all users subscribed to the services
	 * @param token a validate token of an existing administrator
	 * @return the list of all users
	 * @throws NoPermissionException 
	 * @throws InvalidTokenException 
	 */
	@WebMethod(exclude = false)
	public ArrayList<Person> getPersons(@WebParam(name = "token_auth") String token) throws NoPermissionException, InvalidTokenException {
		if (this.getTokenPermission(token) == PERMISSION.ROOT)
			{
				return this.persons;
			}
		else
			throw new NoPermissionException(PERMISSION.ROOT);
	}


	@WebMethod(exclude = false)
	public Person getPersonByID(@WebParam(name = "id_user") int id, @WebParam(name = "token_auth") String token) throws NoPermissionException, InvalidTokenException {
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

	@WebMethod(exclude = true)
	public Person getPersonByToken(@WebParam(name = "token_user") String token) throws InvalidTokenException {
		if (token == "")
		{
			throw new InvalidTokenException("Empty token entry");
		}
		for(Person pers : this.persons){
			if (pers.getToken().equals(token))
			{
				return pers;
			}
		}
		throw new InvalidTokenException("Token is not attributed");
	}
	
	/**
	 * Method to delete user by permission of an administrator
	 * @param id the id of the user to delete
	 * @param token a validate token of an an existing administrator
	 * @return message information if the user is deleted correctly
	 * @throws NoPermissionException 
	 * @throws InvalidTokenException 
	 */
	public String deleteUser(@WebParam(name = "id_user") int id, @WebParam(name = "token_auth") String token) throws NoPermissionException, InvalidTokenException {
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
	 * @throws InvalidTokenException if token given is empty
	 */
	@WebMethod(exclude = true)
	public PERMISSION getTokenPermission(String token) throws InvalidTokenException {
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
	@WebMethod(exclude = true)
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
