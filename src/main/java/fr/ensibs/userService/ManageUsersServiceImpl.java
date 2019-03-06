package fr.ensibs.userService;

import java.security.SecureRandom;
import java.util.ArrayList;

import javax.jws.WebParam;
import javax.jws.WebService;
import fr.ensibs.common.Person;
import fr.ensibs.pizzaService.ManageOrdersServiceImpl;

/**
 * Manage all the users. Each user can sign up, sign in and sign out.
 * But only the administrator can delete a customer and get customer
 * by its id
 * @author Nadjim ABASY and Robin ASPE
 */
@WebService(endpointInterface="fr.ensibs.userService.ManageUsersServiceImpl", serviceName="ManageUsersService", portName="ManageUsersServicePort")
public class ManageUsersServiceImpl implements ManageUsersService {
	public static ArrayList<Person> persons = new ArrayList<Person>();
	public static int id_user = 0;
	
	/**
	 * Sign in a customer or an administrator to the pizzeria services
	 * @param name the password of the user
	 * @param psw the password of the user
	 * @return A token to identify a customer or an administrator
	 */
	public String signIn(@WebParam(name = "name") String name,@WebParam(name = "password") String psw) {
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
	 * @param token the token use during a session
	 * @return message information about if the logout was done successfully
	 */
	public String signOut(String token) {
		for (Person check_bdd_person : this.persons) {
			if (check_bdd_person.getToken().equals(token) == true) {
				for(int i = 0; i < ManageOrdersServiceImpl.orders.size();i++){
				    if(ManageOrdersServiceImpl.orders.get(i).getToken().equals(token)){
				    	ManageOrdersServiceImpl.orders.remove(i--);
				    }
				}
				check_bdd_person.setToken("");
				return "Successful logout, we hope to see you again soon " + check_bdd_person.getName_user() + " (All your unpaid orders are canceled)." ;
			}
		}
		return "Token invalide." ;
	}

	/**
	 * Allow users registration
	 * @param name the name of the user
	 * @param psw the password of the user
	 * @param psw_verification the password of the user
	 * @param isAdmin check if the user is admin or customer
	 * @return Message information about if the subscription was successfully done
	 */
	public String signUp(@WebParam(name = "name") String name, @WebParam(name = "password") String psw, @WebParam(name = "password_verification") String psw_verification, @WebParam(name = "Admin_count_or_not")boolean isAdmin) {
		for( Person check_bdd_person : this.persons ) {
			if (check_bdd_person.getName_user().equals(name) == true && check_bdd_person.getPsw().equals(psw) == true ) {//Really bad idea to use the psw here
				return "This count already exist." ;
			}
		}
		
		if (psw.equals(psw_verification) == true ) {
			if ( name.length() > 1 ) {
				if ( name.length() <= 10 ) {
					Person new_person = new Person( this.id_user++, name, psw, "", isAdmin );
					System.out.println("static id_user => "+this.id_user + " and id_person => "+new_person.getId_person());
					try {
						Thread.sleep( 5000 ) ;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					this.persons.add(new_person) ;
					return "Inscription reussie." ;
				}
				return "The entered name is too long (more than 10 characters)." ;
			}
			return "The name enter is too short (less than 2 characters)." ;
		}
		return "Passwords are different from each other" ;
	}

	/**
	 * Allow to display all users subscribed to the services
	 * @param token a validate token of an existing administrator
	 * @return the list of all users
	 */
	public ArrayList<Person> getPersons(String token) {
		for (Person check_bdd_person : this.persons) {
			if (check_bdd_person.getToken().equals(token) ) {
				if (check_bdd_person.isAdmin()) {
					return this.persons;
				}
				throw new NullPointerException( "Action impossible, you are not an administrator." ) ;
			}		
		}
		throw new NullPointerException( "Invalid token." ) ;
	}

	/**
	 * Allow to get a User from the list of all users
	 * @param id the id of a user
	 * @param token a validate token of an an existing administrator
	 * @return the user
	 */
	public Person getPersonByID(@WebParam(name = "id_user") int id, String token) {
		if (id >= 0 || id < this.id_user) {
			for (Person check_bdd_person : this.persons) {
				if (check_bdd_person.isAdmin() == true && check_bdd_person.getToken().equals(token)) {
					return check_bdd_person;
				}
			}
			
			if (this.persons.get(id).getToken().equals(token)) {
				return this.persons.get(id) ;
			}
			throw new NullPointerException( "Access restricted to the administrator." ) ;
		}
		throw new NullPointerException( "Invalid id." ) ;
	}

	/**
	 * Method to delete user by permission of an administrator
	 * @param id the id of the user
	 * @param token a validate token of an an existing administrator
	 * @return message information if the user is deleted correctly
	 */
	public String deleteUser(@WebParam(name = "id_user") int id, String token) {
		if (id >= 0 && id < this.id_user) {
			for (Person check_bdd_person : this.persons) {
				if (check_bdd_person.getToken().equals(token) == true) {
					Person deleted_user = this.persons.remove(id) ;
					return deleted_user.getName_user() + " is no longer a customer." ;
				}
			}
			return "Only an administrator can delete a user" ;
		}
		return "Invalid Id" ;
	}
	
}
