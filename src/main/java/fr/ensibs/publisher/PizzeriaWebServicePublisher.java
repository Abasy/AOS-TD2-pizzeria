package fr.ensibs.publisher;

import javax.xml.ws.Endpoint;

import fr.ensibs.paymentService.ManagePaymentsService;
import fr.ensibs.paymentService.ManagePaymentsServiceImpl;
import fr.ensibs.pizzaService.ManagePizzasService;
import fr.ensibs.pizzaService.ManagePizzasServiceImpl;
import fr.ensibs.orderService.ManageOrdersService;
import fr.ensibs.orderService.ManageOrdersServiceImpl;
import fr.ensibs.userService.ManageUsersService;
import fr.ensibs.userService.ManageUsersServiceImpl;

public class PizzeriaWebServicePublisher {

	public static void main(String[] args) {
		ManageUsersService userManager = new ManageUsersServiceImpl();
		ManagePizzasService pizzaManager = new ManagePizzasServiceImpl(userManager);
		ManageOrdersService orderManager = new ManageOrdersServiceImpl(userManager, pizzaManager);
		ManagePaymentsService paymentManager = new ManagePaymentsServiceImpl(orderManager, userManager);
		
		System.setProperty( "javax.xml.soap.SAAJMetaFactory" , "com.sun.xml.messaging.saaj.soap.SAAJMetaFactoryImpl");
		Endpoint.publish( "http://localhost:9991/ws/pizzeria/manage_users", userManager);
		Endpoint.publish( "http://localhost:9991/ws/pizzeria/manage_pizzas", pizzaManager);
		Endpoint.publish( "http://localhost:9991/ws/pizzeria/manage_orders", orderManager);
		Endpoint.publish( "http://localhost:9991/ws/pizzeria/manage_payments", paymentManager);
	}

}
