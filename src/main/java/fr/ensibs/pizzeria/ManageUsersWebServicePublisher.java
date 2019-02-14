package fr.ensibs.pizzeria;

import javax.xml.ws.Endpoint;

public class ManageUsersWebServicePublisher {

	public static void main(String[] args) {
		Endpoint.publish("http://localhost:9991/ws/manageusersservice", new ManageUsersServiceImpl());
	}

}
