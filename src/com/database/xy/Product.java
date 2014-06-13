package com.database.xy;

public class Product
{
	private String product_name;
	private String product_description;
	private double product_price = 0;
	
	public static final String PRODUCT_DB_NAME = "Produkt";
	public static final String PRODUCT_DB_DESCRIPTION = "Beschreibung";
	public static final String PRODUCT_DB_PRICE = "Preis";
	
	public Product() { }
	
	public Product(String name, String description, double price)
	{
		product_price = price;
		product_name = name;
		product_description = description;
	}
	
	/* Setter & Getter */
	
	public void setProduct_name(String name)
	{
		product_name = name;
	}
	
	public void setProduct_description(String description)
	{
		product_description = description;
	}
	
	public void setProduct_price(double price)
	{
		product_price = price;
	}
	
	public String getProduct_name()
	{
		return product_name;
	}
	
	public String getProduct_description()
	{
		return product_description;
	}
	
	public double getProduct_price()
	{
		return product_price;
	}
}
