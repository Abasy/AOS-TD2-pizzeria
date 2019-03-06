package fr.ensibs.paymentService;

import javax.jws.WebService;

@WebService(name = "ManagePaymentsService", targetNamespace = "http://pizzeria.ensibs.fr/paiement")
public interface ManagePaymentsService {
	/**
	 * Method to validate payment of an order by a validate token and an existing non payed order
	 * @param id_order the id of the order
	 * @param token a validate token
	 * @return Set the status of the order, and display an information message of the operation done
	 */
	String payOrder(int id_order, String token) ;
	
	/**
	 * Method to visualize the bill or the order if it's not yet payed
	 * @param id_order the id of the order 
	 * @param token a validate existing token
	 * @return the information about the order
	 */
	String previsionner_facture(int id_order, String token) ;
}
