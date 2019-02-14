package fr.ensibs.pizzeria;

import javax.jws.WebService;

/**
 * 
 * @author ABASY Nadjim aka Darkh-Ollen and ASPE Robin
 *
 */
@WebService(name = "ManageUsersService", targetNamespace="http://pizzeria.ensibs.fr")
public interface ManageUsersService {
	
	/**
	 * Sign in a customer or an administrator
	 * @return A token to identify a customer or an administrator
	 */
	String signIn();
	
	/**
	 * Sign out a customer or an administrator
	 */
	void signOut();
	
	/**
	 * Allow users registration
	 * @return A token to identify a customer or an administrator
	 */
	String signUp();
}
