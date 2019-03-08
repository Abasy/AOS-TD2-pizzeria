package fr.ensibs.orderService;

import java.util.ArrayList;

import javax.jws.WebParam;
import javax.jws.WebService;

import fr.ensibs.common.InvalidTokenException;
import fr.ensibs.common.NoPermissionException;
import fr.ensibs.common.Order;
import fr.ensibs.common.Pizza;
import fr.ensibs.common.Person.PERMISSION;
import fr.ensibs.userService.ManageUsersService;
import fr.ensibs.pizzaService.ManagePizzasService;

@WebService(endpointInterface="fr.ensibs.orderService.ManageOrdersServiceImpl", serviceName="ManageOrdersService", portName="ManageOrdersServicePort")
public class ManageOrdersServiceImpl implements ManageOrdersService {
	
	private ArrayList<Order> orders;
	private ArrayList<Integer> free_id;
	private int last_id;
	private ManageUsersService userManager;
	private ManagePizzasService pizzaManager;
	
	/**
	 * Constructor
	 * @param userManager The user manager used to give the users infos and permissions.
	 */
	public ManageOrdersServiceImpl( ManageUsersService userManager, ManagePizzasService pizzaManager) {
		this.orders = new ArrayList<Order>();
		this.free_id = new ArrayList<Integer>();
		this.last_id = 0;
		this.userManager = userManager;
		this.pizzaManager = pizzaManager;
	}
	
	public Order orderPizza(@WebParam(name = "id_pizza") int id_pizza, @WebParam(name = "quantity") int quantity, @WebParam(name = "token_auth") String token) throws NoPermissionException, InvalidTokenException 
	{		
		if (userManager.getTokenPermission(token) == PERMISSION.USER)
		{
			double price = 0 ;
			Pizza pizza = this.pizzaManager.getPizzaById(id_pizza);
			if (pizza != null)
			{
				price = pizza.getPrice_pizza();
				Order order =  new Order( this.next_id_order() , pizza.getName_pizza(), userManager.getPersonByToken(token).getId_person(), quantity , price * quantity );
				order.setStatus(Order.STATUS.ACTIVE);
				this.orders.add(order);
				return order;
			}
			return null;
		}
		else 
			throw new NoPermissionException(PERMISSION.USER);
	}

	public Order cancelOrderPizza(@WebParam(name = "id") int id_order, @WebParam(name = "token_auth") String token) throws NoPermissionException, InvalidTokenException {
		if (userManager.getTokenPermission(token) == PERMISSION.USER)
		{
			Order order = this.getOrderById(id_order);
			if (order != null)
			{
				if(order.getStatus() == Order.STATUS.ACTIVE) 
				{
					this.orders.remove(order);
					this.free_id.add(id_order);
					order.setStatus(Order.STATUS.CANCELLED);
					return order;
				}
			}
			return null;
		}
		else 
			throw new NoPermissionException(PERMISSION.USER);
	}
	

	public ArrayList<Order> getMyOrders(@WebParam(name = "token") String token) throws NoPermissionException, InvalidTokenException {
		if (userManager.getTokenPermission(token) == PERMISSION.USER)
		{
			ArrayList<Order> myOrders = new ArrayList<Order>();
			int user_id = this.userManager.getPersonByToken(token).getId_person();
			for(Order order : orders) {
				if(order.getUserId() == user_id);
					myOrders.add(order);
			}
			return myOrders ;
		}
		else 
			throw new NoPermissionException(PERMISSION.USER);
	}

	/**
	 * Private method getting the next free ID for a new order.
	 * @return A new ID that can be used to sign in a new order
	 */
	private int next_id_order() {
		
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
	
	/**
	 * Private method to get an Order from the list of orders
	 * @param order_id the id of an order
	 * @return the Order object associated to that ID, or null
	 */
	private Order getOrderById (@WebParam(name = "id") int order_id) {
		for (Order order: this.orders) {
			if (order.getId_order() == order_id)
			{
				return order;
			}
		}
		return null;
	}

}
