package fr.ensibs.userService;

import java.util.ArrayList;

import javax.jws.WebService;

import fr.ensibs.common.Person;

/**
 * Manage all the users. Each user can sign up, sign in and sign out.
 * But only the administrator can delete a customer and get customer
 * by its id
 * @author Nadjim ABASY and Robin ASPE
 */
@WebService(name = "ManageUsersService", targetNamespace="http://pizzeria.ensibs.fr/User")
public interface ManageUsersService {
	
	/**
	 * Sign in a customer or an administrator to the pizzeria services
	 * @param name the password of the user
	 * @param psw the password of the user
	 * @return A token to identify a customer or an administrator
	 */
	String signIn(String name, String psw);
	
	/**
	 * Sign out a customer or an administrator
	 * @param token the token use during a session
	 * @return message information about if the logout was done successfully
	 */
	String signOut(String token);
	
	/**
	 * Allow users registration
	 * @param name the name of the user
	 * @param psw the password of the user
	 * @param psw_verification the password of the user
	 * @param isAdmin check if the user is admin or customer
	 * @return Message information about if the subscription was successfully done
	 */
	String signUp(String name, String psw, String psw_verification, boolean isAdmin);
	
	/**
	 * Allow to display all users subscribed to the services
	 * @param token a validate token of an existing administrator
	 * @return the list of all users
	 */
	ArrayList <Person> getPersons(String token);
	
	/**
	 * Allow to get a User from the list of all users
	 * @param id the id of a user
	 * @param token a validate token of an an existing administrator
	 * @return the user
	 */
	Person getPersonByID(int id , String token);
	
	/**
	 * Method to delete user by permission of an administrator
	 * @param id the id of the user
	 * @param token a validate token of an an existing administrator
	 * @return message information if the user is deleted correctly
	 */
	String deleteUser(int id , String token );
}
