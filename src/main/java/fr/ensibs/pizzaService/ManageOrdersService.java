package fr.ensibs.pizzaService;

import java.util.ArrayList;

import javax.jws.WebParam;
import javax.jws.WebService;

import fr.ensibs.common.Order;
import fr.ensibs.common.Pizza;

@WebService(name = "ManageOrdersService", targetNamespace = "http://pizzeria.ensibs.fr/manage_orders")
public interface ManageOrdersService {
	
	/**
	 * Method to order a pizza using the id of the pizza desired
	 * @param id_pizza the name of the pizza
	 * @param quantity the number of the pizza
	 * @return message information to indicate whether the order is taken or not
	 */
	String orderPizza(@WebParam(name = "id_pizza") int id_pizza, @WebParam(name = "quantity_of_pizza") int quantity, String token);
	
	/**
	 * Method to cancel an order not already payed
	 * @param id the id of the order
	 * @param token a validate token of an existing connected user
	 * @return message information to indicate whether the command is canceled or not
	 */
	String cancelOrderPizza(@WebParam(name = "id_of_order") int id_order, String token);
	
	/**
	 * Method to display all the order taken by an user identified by his token
	 * @param token a validate token 
	 * @return the list of orders taken by an existing user
	 */
	ArrayList<Order> getListOrders(String token) ;
	
	/**
	 * Method to add a pizza to the menu 
	 * @param name_pizza the name of the pizza we want to add
	 * @param description the description of the pizza
	 * @param price the price of the pizza
	 * @return message information to indicate whether the pizza is added or not
	 */
	String addPizza(@WebParam(name = "name_pizza") String name_pizza, @WebParam(name = "description") String description, @WebParam(name = "price") double price, String token);
	
	/**
	 * Method to delete a pizza from the menu
	 * @param name_pizza the name of the pizza
	 * @param token a validate token
	 * @return message information to indicate whether the pizza is deleted or not
	 */
	String deletePizza(@WebParam(name = "name_pizza") String name_pizza, String token);
	
	/**
	 * Method to display the menu of the pizzeria
	 * @return the list of all pizza in the array list
	 */
	ArrayList<Pizza> getMenu();
	
	/**
	 * Method to get a Pizza from the list of pizza
	 * @param name_pizza the name of the pizza 
	 * @return A Pizza from the array list
	 */
	Pizza getPizzaById (@WebParam(name = "name_pizza") String name_pizza);
}
