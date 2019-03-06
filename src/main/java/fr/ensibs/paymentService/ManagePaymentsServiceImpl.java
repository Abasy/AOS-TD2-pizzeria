package fr.ensibs.paymentService;

import java.util.ArrayList;

import javax.jws.WebService;

import fr.ensibs.common.Order;
import fr.ensibs.common.Person;
import fr.ensibs.pizzaService.ManageOrdersServiceImpl;
import fr.ensibs.userService.ManageUsersServiceImpl;

@WebService( endpointInterface = "fr.ensibs.paymentService.ManagePaymentsServiceImpl" , serviceName = "ManagePaymentsService" , portName = "ManagePaymentsServicePort" )
public class ManagePaymentsServiceImpl implements ManagePaymentsService {

	private ArrayList<Person> persons = ManageUsersServiceImpl.persons ;
	private ArrayList<Order> orders = ManageOrdersServiceImpl.orders;
	
	public String payOrder(int id_order, String token) {
		for(Person person : persons) {
			if(person.getToken().equals(token)) {
				for(Order order : orders) {
					if(order.getId_order() == id_order && order.getToken().equals(token) && order.getStatus().equals("not paid")) {
						order.setStatus("paid");
						return "Detail of the invoice : Pizzas : "+order.getName_pizza() +
								" , Quantity : "+order.getQuantity() +
								" , Total price : "+order.getTotal_price();
					}
				}
				return "The order number you entered does not exist.";
			}
		}
		return "Your token is invalid";
	}

	public String previewInvoice(int id_order, String token) {
		for(Person person : persons) {
			if(person.getToken().equals(token)) {
				for(Order order : orders) {
					if(order.getId_order() == id_order && order.getToken().equals(token)) {
						return "Detail of the invoice  : "+order;
					}
				}
				return "The order number you entered does not exist.";
			}
		}
		return "Your token is invalid";
	}

}
