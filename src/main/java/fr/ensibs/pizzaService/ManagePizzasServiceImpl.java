package fr.ensibs.pizzaService;

import java.util.ArrayList;

import javax.jws.WebParam;
import javax.jws.WebService;

import fr.ensibs.common.InvalidTokenException;
import fr.ensibs.common.NoPermissionException;
import fr.ensibs.common.Pizza;
import fr.ensibs.common.Person.PERMISSION;
import fr.ensibs.userService.ManageUsersService;

@WebService(endpointInterface="fr.ensibs.pizzaService.ManagePizzasServiceImpl", serviceName="ManagePizzasService", portName="ManagePizzasServicePort")
public class ManagePizzasServiceImpl implements ManagePizzasService {
	
	private ArrayList<Pizza> pizzas;
	private ArrayList<Integer> free_id;
	private int last_id;
	private ManageUsersService userManager;
	
	/**
	 * Constructor
	 * @param userManager The user manager used to give the users infos and permissions.
	 */
	public ManagePizzasServiceImpl( ManageUsersService userManager) {
		this.pizzas = new ArrayList<Pizza>();
		this.free_id = new ArrayList<Integer>();
		this.last_id = 0;
		this.userManager = userManager;
	}

	public Pizza addPizza(@WebParam(name = "name_pizza") String name_pizza, @WebParam(name = "description") String description, @WebParam(name="Price") double price, @WebParam(name = "token_auth") String token) throws NoPermissionException, InvalidTokenException {
		if (userManager.getTokenPermission(token) == PERMISSION.ROOT)
		{
			if (name_pizza.equals("") == false) 
			{
				if (this.getPizzaByName(name_pizza) == null)
				{
					if (description.equals("") == false ) 
					{
						if (price > 0) {
							Pizza p = new Pizza(this.next_id_pizza(), name_pizza, description, price);
							this.pizzas.add(p) ;
							return p;
						}
					}
				}
			}
			return null;
		}
		else
			throw new NoPermissionException(PERMISSION.ROOT);
	}
	
	
	public Pizza deletePizza(@WebParam(name = "id") int id, @WebParam(name = "token_auth") String token) throws NoPermissionException, InvalidTokenException {
		if (userManager.getTokenPermission(token) == PERMISSION.ROOT)
		{
			Pizza pizza = this.getPizzaById(id);
			if (pizza != null)
			{
				this.pizzas.remove(pizza);
				this.free_id.add(pizza.getId_pizza());
				return pizza ;
			}
			else return null;
		}
		else
			throw new NoPermissionException(PERMISSION.ROOT);
	}
	
	public ArrayList<Pizza> getMenu() {
		return this.pizzas ;
	}

	public Pizza getPizzaById(@WebParam(name = "id") int id) {
		for ( Pizza check_bdd_pizza : pizzas ) {
			if (check_bdd_pizza.getId_pizza() == id) {
				return check_bdd_pizza ;
			}
		}
		return null;
	}
	
	public Pizza getPizzaByName(@WebParam(name = "name") String name) {
		for ( Pizza check_bdd_pizza : pizzas ) {
			if (check_bdd_pizza.getName_pizza().equals(name)) {
				return check_bdd_pizza ;
			}
		}
		return null;
	}

	/**
	 * Private method getting the next free ID for a new pizza.
	 * @return A new ID that can be used to sign in a new pizza
	 */
	private int next_id_pizza() {
		
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
