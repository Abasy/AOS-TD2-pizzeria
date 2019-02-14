package fr.ensibs.pizzeria;

import javax.jws.WebService;

@WebService(endpointInterface="fr.ensibs.pizzeria.ManageUsersService", serviceName="ManageUsersService", portName="PizzeriaPort")
public class ManageUsersServiceImpl implements ManageUsersService {

	public String signIn() {
		return "SignIn";
	}

	public void signOut() {
		System.out.println("SignOut");
	}

	public String signUp() {
		return "SignUp";
	}
	
}
