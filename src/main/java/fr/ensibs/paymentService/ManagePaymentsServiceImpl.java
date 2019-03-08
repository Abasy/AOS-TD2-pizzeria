package fr.ensibs.paymentService;

import java.util.ArrayList;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import fr.ensibs.common.InvalidTokenException;
import fr.ensibs.common.NoPermissionException;
import fr.ensibs.common.Order;
import fr.ensibs.common.Order.STATUS;
import fr.ensibs.common.Person;
import fr.ensibs.common.Person.PERMISSION;
import fr.ensibs.orderService.ManageOrdersService;
import fr.ensibs.userService.ManageUsersService;

@WebService( endpointInterface = "fr.ensibs.paymentService.ManagePaymentsServiceImpl" , serviceName = "ManagePaymentsService" , portName = "ManagePaymentsServicePort" )
public class ManagePaymentsServiceImpl implements ManagePaymentsService {

	private ManageOrdersService orderManager;
	private ManageUsersService  userManager;
	
	public ManagePaymentsServiceImpl(ManageOrdersService orderManager, ManageUsersService userManager) {
		this.orderManager = orderManager;
		this.userManager = userManager;
	}
	
	public Order payOrder(@WebParam(name = "id_order") int id_order, @WebParam(name = "token_auth") String token) throws NoPermissionException, InvalidTokenException {
		if (userManager.getTokenPermission(token) == PERMISSION.USER)
		{
			ArrayList<Order> myOrders = orderManager.getMyOrders(token);
			if (!myOrders.isEmpty())
			{
				for (Order order : myOrders)
				{
					if (id_order == order.getId_order())
					{
						if (order.getStatus() == STATUS.ACTIVE)
						{
							order.setStatus(STATUS.PAID);
							return order;
						}
					}
				}
			}
			return null;
		}
		else
			throw new NoPermissionException(PERMISSION.USER);
	}
	
	@WebResult(name = "return_message")
	public String previewInvoice(@WebParam(name = "token_user") String token) throws NoPermissionException, InvalidTokenException 
	{
		if (userManager.getTokenPermission(token) == PERMISSION.USER)
		{
			String result = new String();
			ArrayList<Order> myOrders = orderManager.getMyOrders(token);
			double totalPrice = 0;
			result = result.concat("--------------------------\n");
			result = result.concat("---     Your orders:   ---\n");
			result = result.concat("--------------------------\n");
			for (Order order : myOrders)
			{
				totalPrice = totalPrice + order.getTotal_price();
				result = result.concat(order.toString());
				result = result.concat("\n");
			}
			result = result.concat("--------------------------\n");
			result = result.concat("--- Total price: " + totalPrice);
			return result;
		}
		else throw new NoPermissionException(PERMISSION.USER);
	}

}
