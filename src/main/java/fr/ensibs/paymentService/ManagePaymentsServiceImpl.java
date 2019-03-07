package fr.ensibs.paymentService;

import java.util.ArrayList;

import javax.jws.WebService;

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
	
	public Order payOrder(int id_order, String token) throws NoPermissionException {
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
	
	public String previewInvoice(String token) throws NoPermissionException 
	{
		if (userManager.getTokenPermission(token) == PERMISSION.USER)
		{
			Person p = userManager.getPersonByToken(token);
			String result = "";
			ArrayList<Order> myOrders = orderManager.getMyOrders(token);
			double totalPrice = 0;
			for (Order order : myOrders)
			{
				totalPrice += order.getTotal_price();
			}
			result.concat("--------------------------\n");
			result.concat("---     Your orders:   ---\n");
			result.concat("--------------------------\n");
			result.concat(myOrders.toString());
			result.concat("\n");
			result.concat("--------------------------\n");
			result.concat("--- Total price: " + totalPrice);
			return result;
		}
		else throw new NoPermissionException(PERMISSION.USER);
	}

}
