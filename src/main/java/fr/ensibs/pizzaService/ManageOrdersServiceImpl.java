package fr.ensibs.pizzaService;

import java.util.ArrayList;

import fr.ensibs.common.Order;
import fr.ensibs.common.Person;
import fr.ensibs.common.Pizza;
import fr.ensibs.userService.ManageUsersServiceImpl;

public class ManageOrdersServiceImpl implements ManageOrdersService {
	
	public static ArrayList<Order> orders = new ArrayList<Order>() ;
	private ArrayList<Pizza> pizzas = new ArrayList<Pizza>() ;
	private ArrayList<Person> persons = ManageUsersServiceImpl.persons ;
	
	public String orderPizza(int id_pizza, int quantity, String token) {
		if(this.persons.size()> 0) {
			for ( Person check_bdd_person : this.persons ) {
				if (check_bdd_person.getToken() != null &&  check_bdd_person.getToken().equals(token)) {
					if ( check_bdd_person.isAdmin() == false ) {
						double price = 0 ;
						for( Pizza check_bdd_pizza : this.pizzas ) {
							if ( check_bdd_pizza.getId_pizza() ==  id_pizza  ) {
								price = check_bdd_pizza.getPrice_pizza();
								Order order =  new Order( this.orders.size(), check_bdd_pizza.getName_pizza(), token, quantity , price * quantity );
								order.setStatus("not paid");
								this.orders.add(order) ;
								return "Successful order" ;
							}
						}
						return "Incorrect pizza name";
					}
					return "Only customers can order pizza. Bye bye Admin !" ;
				}
			}
			return "Invalid token" ;
		}
		else
		{
			return "No users in the database.";
		}
	}

	public String cancelOrderPizza(int id_order, String token) {
		if (id_order >= 0 && id_order < this.orders.size()) {
			for(Person check_bdd_person : persons) {
				if (check_bdd_person.getToken().equals(token)) {
					if(this.orders.get(id_order).getStatus().equals("not paid")) {
						this.orders.remove(id_order) ;
						return "The order number " + id_order + " is canceled." ;
					}else {
						return "This orders is already paid.";
					}
				}
			}
			return "Invalid token.";
			
		}
		return "Invalid id order" ;
	}

	public ArrayList<Order> getListOrders(String token) {
		ArrayList<Order> myOrders = new ArrayList<Order>();
		for (Person check_bdd_person : this.persons) {
			if (check_bdd_person.getToken().equals(token)) {
				for(Order order : orders) {
					if(order.getToken().equals(token))
						myOrders.add(order);
				}
				return myOrders ;
			}
		}
		throw new NullPointerException( "Token invalid." ) ;
	}

	public String addPizza(String name_pizza, String description, double price, String token) {
		for (Person check_bdd_person : this.persons ) {
			if ( check_bdd_person.getToken().equals(token)) {
				if ( check_bdd_person.isAdmin() == true ) {
					for (Pizza check_bdd_pizza : this.pizzas ) {
						if (check_bdd_pizza.getName_pizza().equals(name_pizza) == true ) {
							return "This pizza is already available for sale." ;
						}
					}
					if (name_pizza.equals("") == false) {
						if (description.equals("") == false ) {
							if (price > 6) {
								this.pizzas.add(new Pizza(this.pizzas.size(), name_pizza, description, price)) ;
								return "The pizza " + name_pizza + " is now available for sale." ;
							}
							return "The pizzeria will go bankrupt if you sell pizzas for less than 7 euros." ;
						}
						return "Please fill in a description of the pizza before adding it." ;
					}
					return "It is impossible to add a pizza without giving it a name." ;
				}
				return "Only administrators can add a new pizza to the map." ;
			}
		}
		return "Invalid Token." ;
	}

	public String deletePizza(String name_pizza, String token) {
		for ( Person check_bdd_person : persons ) {
			if ( check_bdd_person.getToken().equals(token)) {
				if ( check_bdd_person.isAdmin() == true) {
					for ( Pizza check_bdd_pizza : this.pizzas ) {
						if ( check_bdd_pizza.getName_pizza().equals(name_pizza)) {
							this.pizzas.remove( check_bdd_pizza ) ;
							return "The pizza " + name_pizza + " is no longer available for sale." ;
						}
					}
				}
				return "Only administrators can remove a pizza from the card." ;
			}
		}
		return "Invalid Token." ;
	}

	public ArrayList<Pizza> getMenu() {
		return this.pizzas ;
	}

	public Pizza getPizzaById(String name_pizza) {
		if (name_pizza == "") {
			throw new NullPointerException( "Name is null." ) ;
		}
		
		for ( Pizza check_bdd_pizza : pizzas ) {
			if (name_pizza.equals( check_bdd_pizza.getName_pizza())) {
				return check_bdd_pizza ;
			}
		}
		throw new NullPointerException( "this pizza does not exist." ) ;
	}

}
