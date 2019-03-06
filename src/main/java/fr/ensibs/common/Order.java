package fr.ensibs.common;

public class Order {
	private int id_order; 
	private String name_pizza;
	private String token;
	private int quantity;
	private double total_price;
	private String status;
	
	/**
	 * Constructor with parameters to initiate the order
	 * @param id id of the order
	 * @param nom_pizza name of pizza
	 * @param token token of the user
	 * @param quantiter the quantity of the order
	 * @param prix the total price
	 */
	public Order (int id, String name_pizza, String token, int quantity, double price) {
		this.id_order = id;
		this.name_pizza = name_pizza;
		this.token = token;
		this.quantity = quantity;
		this.total_price = price;
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
	 * @return the token
	 */
	public String getToken() {
		return token;
	}



	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}



	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}



	public String toString() {
		return "Order number "+this.id_order+" : [pizza name : "+this.name_pizza+", quantity : "+this.quantity+
				", total price : "+this.total_price+", status : "+this.status+"]";
	}
}
