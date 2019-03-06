package fr.ensibs.publisher;

import javax.xml.ws.Endpoint;

import fr.ensibs.paymentService.ManagePaymentsServiceImpl;
import fr.ensibs.pizzaService.ManageOrdersServiceImpl;
import fr.ensibs.userService.ManageUsersServiceImpl;

public class PizzeriaWebServicePublisher {

	public static void main(String[] args) {
		System.setProperty( "javax.xml.soap.SAAJMetaFactory" , "com.sun.xml.messaging.saaj.soap.SAAJMetaFactoryImpl") ;
		Endpoint.publish( "http://localhost:9991/ws/pizzeria/manage_users", new ManageUsersServiceImpl()) ;
		Endpoint.publish( "http://localhost:9991/ws/pizzeria/manage_orders", new ManageOrdersServiceImpl()) ;
		Endpoint.publish( "http://localhost:9991/ws/pizzeria/manage_payments", new ManagePaymentsServiceImpl());
	}

}
