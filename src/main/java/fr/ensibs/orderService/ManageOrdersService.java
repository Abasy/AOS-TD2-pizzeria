package fr.ensibs.orderService;

import java.util.ArrayList;

import javax.jws.WebParam;
import javax.jws.WebService;

import fr.ensibs.common.NoPermissionException;
import fr.ensibs.common.Order;
import fr.ensibs.common.Pizza;

@WebService(name = "ManageOrdersService", targetNamespace = "http://pizzeria.ensibs.fr/manage_orders")
public interface ManageOrdersService {
	
	/**
	 * Method to order a pizza using the id of the pizza desired
	 * @param id_pizza the name of the pizza
	 * @param quantity the number of the pizza
	 * @return message information to indicate whether the order is taken or not
	 * @throws NoPermissionException 
	 */
	Order orderPizza(@WebParam(name = "id_pizza") int id_pizza, @WebParam(name = "quantity_of_pizza") int quantity, String token) throws NoPermissionException;
	
	/**
	 * Method to cancel an order not already payed
	 * @param id the id of the order
	 * @param token a validate token of an existing connected user
	 * @return message information to indicate whether the command is canceled or not
	 * @throws NoPermissionException 
	 */
	Order cancelOrderPizza(@WebParam(name = "id_of_order") int id_order, String token) throws NoPermissionException;
	
	/**
	 * Method to display all the order taken by an user identified by his token
	 * @param token a validate token 
	 * @return the list of orders taken by an existing user
	 * @throws NoPermissionException 
	 */
	ArrayList<Order> getMyOrders(String token) throws NoPermissionException ;
}
