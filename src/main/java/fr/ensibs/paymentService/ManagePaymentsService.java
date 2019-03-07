package fr.ensibs.paymentService;

import javax.jws.WebParam;
import javax.jws.WebService;

import fr.ensibs.common.NoPermissionException;
import fr.ensibs.common.Order;

@WebService(name = "ManagePaymentsService", targetNamespace = "http://pizzeria.ensibs.fr/manage_payments")
public interface ManagePaymentsService {
	/**
	 * Method to validate payment of an order by a validate token and an existing non payed order
	 * @param id_order the id of the order
	 * @param token a validate token
	 * @return Set the status of the order, and display an information message of the operation done
	 * @throws NoPermissionException 
	 */
	Order payOrder(@WebParam(name = "id_order") int id_order, String token) throws NoPermissionException ;
	
	/**
	 * Method to visualize the bill or the order if it's not yet payed
	 * @param token a validate existing token
	 * @return the information about the order
	 * @throws NoPermissionException 
	 */
	String previewInvoice(@WebParam(name = "token") String token) throws NoPermissionException ;
}
