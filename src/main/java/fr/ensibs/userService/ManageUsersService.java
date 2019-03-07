package fr.ensibs.userService;

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
@WebService(name = "ManageUsersService", targetNamespace="http:///pizzeria.ensibs.fr/manage_users")
public interface ManageUsersService {
	
	/**
	 * Sign in a customer or an administrator to the pizzeria services
	 * @param name the password of the user
	 * @param psw the password of the user
	 * @return A token to identify a customer or an administrator
	 */
	String signIn(@WebParam(name = "name") String name, @WebParam(name = "password") String psw);
	
	/**
	 * Sign out a customer or an administrator
	 * @param token the token use during a session
	 * @return message information about if the logout was done successfully
	 * @throws InvalidTokenException 
	 */
	String signOut(String token) throws InvalidTokenException;
	
	/**
	 * Allow users registration
	 * @param name the name of the user
	 * @param psw the password of the user
	 * @param psw_verification the password of the user
	 * @param permission the permission level given to this user
	 * @return Message information about if the subscription was successfully done
	 */
	String signUp(@WebParam(name = "name") String name, @WebParam(name = "password") String psw, @WebParam(name = "password_verification") String psw_verification, @WebParam(name = "permission level")PERMISSION permission);
	
	/**
	 * Allow to display all users subscribed to the services
	 * @param token a validate token of an existing administrator
	 * @return the list of all users
	 * @throws NoPermissionException 
	 */
	ArrayList <Person> getPersons(String token) throws NoPermissionException;
	
	/**
	 * Allow to get a User from the list of all users
	 * @param id the id of a user
	 * @param token a validate token of an an existing administrator
	 * @return the user
	 * @throws NoPermissionException 
	 */
	Person getPersonByID(@WebParam(name = "id_user") int id , String token) throws NoPermissionException;
	
	/**
	 * Allow to get a User from the list of all users with its token
	 * @param token the token looked up
	 * @return the user
	 * @throws NoPermissionException 
	 */
	Person getPersonByToken(@WebParam(name = "token_user") String token);
	
	/**
	 * Get the permission of the user represented by the token
	 * @param token A token to test
	 * @return the user permission
	 */
	Person.PERMISSION getTokenPermission(@WebParam(name = "token") String token);
	
	/**
	 * Method to delete user by permission of an administrator
	 * @param id the id of the user
	 * @param token a validate token of an an existing administrator
	 * @return message information if the user is deleted correctly
	 * @throws NoPermissionException 
	 */
	String deleteUser(@WebParam(name = "id_user") int id , String token) throws NoPermissionException;
	

}
