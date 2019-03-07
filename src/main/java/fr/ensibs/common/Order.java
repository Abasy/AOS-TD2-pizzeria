package fr.ensibs.common;

public class Order {
	
	public enum STATUS {ACTIVE, CANCELLED, PAID}
	
	private int id_order; 
	private String name_pizza;
	private int user_id;
	private int quantity;
	private double total_price;
	private STATUS status;
	
	/**
	 * Constructor with parameters to initiate the order
	 * @param id Unique identifier of the order
	 * @param name_pizza Name of pizza in the order
	 * @param user_id ID of the user that ordered this order
	 * @param quantity Number of pizzas wanted for this order
	 * @param price the total price of the order
	 */
	public Order (int id, String name_pizza, int user_id, int quantity, double price) {
		this.id_order = id;
		this.name_pizza = name_pizza;
		this.user_id = user_id;
		this.quantity = quantity;
		this.total_price = price;
		this.status = STATUS.ACTIVE;
	}
	
	
	
	/**
	 * @return the id_order
	 */
	public int getId_order() {
		return id_order;
	}



	/**
	 * @param id_order the id_order to set
	 */
	public void setId_order(int id_order) {
		this.id_order = id_order;
	}



	/**
	 * @return the name_pizza
	 */
	public String getName_pizza() {
		return name_pizza;
	}



	/**
	 * @param name_pizza the name_pizza to set
	 */
	public void setName_pizza(String name_pizza) {
		this.name_pizza = name_pizza;
	}



	/**
	 * @return the ID of the user that made this order
	 */
	public int getUserId() {
		return user_id;
	}



	/**
	 * @param user_id The ID of the user that made this order
	 */
	public void setUserId(int user_id) {
		this.user_id = user_id;
	}



	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}



	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}



	/**
	 * @return the total_price
	 */
	public double getTotal_price() {
		return total_price;
	}



	/**
	 * @param total_price the total_price to set
	 */
	public void setTotal_price(double total_price) {
		this.total_price = total_price;
	}



	/**
	 * @return the status of the order (active, cancelled, paid)
	 */
	public STATUS getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(STATUS status) {
		this.status = status;
	}



	public String toString() {
		return "Order number "+this.id_order+" : [pizza name : "+this.name_pizza+", quantity : "+this.quantity+
				", total price : "+this.total_price+", status : "+this.status+"]";
	}
}
