package fr.ensibs.common;

import javax.xml.bind.annotation.XmlElement;

/**
 * Class that design a single Pizza object
 * @author Nadjim ABASY and Robin ASPE
 */
public class Pizza {
	private int id_pizza;
	private String name_pizza ;
	private String description_pizza ;
	private double price_pizza ;
	
	/**
	 * Constructor with parameters
	 * @param id Unique identifier of the pizza
	 * @param name Name of the pizza
	 * @param description Description of the pizza
	 * @param price Price for a single pizza
	 */
	public Pizza(int id, String name, String description, double price) {
		this.id_pizza = id;
		this.name_pizza = name ;
		this.description_pizza = description ;
		this.price_pizza = price ;
	}

	/**
	 * @return the id_pizza
	 */
	public int getId_pizza() {
		return id_pizza;
	}

	/**
	 * @param id_pizza the id_pizza to set
	 */
	public void setId_pizza(int id_pizza) {
		this.id_pizza = id_pizza;
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
	 * @return the description_pizza
	 */
	public String getDescription_pizza() {
		return description_pizza;
	}

	/**
	 * @param description_pizza the description_pizza to set
	 */
	public void setDescription_pizza(String description_pizza) {
		this.description_pizza = description_pizza;
	}

	/**
	 * @return the price_pizza
	 */
	public double getPrice_pizza() {
		return price_pizza;
	}

	/**
	 * @param price_pizza the price_pizza to set
	 */
	public void setPrice_pizza(double price_pizza) {
		this.price_pizza = price_pizza;
	}
}
