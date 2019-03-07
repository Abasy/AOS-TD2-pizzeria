package fr.ensibs.pizzaService;

import java.util.ArrayList;

import javax.jws.WebParam;
import javax.jws.WebService;

import fr.ensibs.common.NoPermissionException;
import fr.ensibs.common.Order;
import fr.ensibs.common.Pizza;

@WebService(name = "ManagePizzasService", targetNamespace = "http://pizzeria.ensibs.fr/manage_pizzas")
public interface ManagePizzasService {
	
	/**
	 * Method to add a pizza to the menu 
	 * @param name_pizza the name of the pizza we want to add
	 * @param description the description of the pizza
	 * @param price the price of the pizza
	 * @return message information to indicate whether the pizza is added or not
	 * @throws NoPermissionException 
	 */
	Pizza addPizza(@WebParam(name = "name_pizza") String name_pizza, @WebParam(name = "description") String description, @WebParam(name = "price") double price, String token) throws NoPermissionException;
	
	/**
	 * Method to delete a pizza from the menu
	 * @param id_pizza the id of the pizza to delete
	 * @param token a validate token
	 * @return message information to indicate whether the pizza is deleted or not
	 * @throws NoPermissionException 
	 */
	Pizza deletePizza(@WebParam(name = "name_pizza") int id_pizza, String token) throws NoPermissionException;
	
	/**
	 * Method to display the menu of the pizzeria
	 * @return the list of all pizza in the array list
	 */
	ArrayList<Pizza> getMenu();
	
	/**
	 * Method to get a Pizza from the list of pizza
	 * @param id_pizza the id of the pizza 
	 * @return A Pizza from the array list or null
	 */
	Pizza getPizzaById (@WebParam(name = "id_pizza") int id_pizza);
	
	/**
	 * Method to get a Pizza from the list of pizza
	 * @param name the name looked up
	 * @return A Pizza from the array list or null
	 */
	Pizza getPizzaByName (@WebParam(name = "name") String name);
	
}
