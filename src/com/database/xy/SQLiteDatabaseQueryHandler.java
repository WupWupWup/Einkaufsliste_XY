package com.database.xy;


import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class SQLiteDatabaseQueryHandler
{
	
	private static SQLiteDatabaseHandler db;
	
	public SQLiteDatabaseQueryHandler(Context con)
	{
		db = new SQLiteDatabaseHandler(con);
		db.getWritableDatabase();
	}
	
//	public static Cursor fetchGroup() 
//	{
//		SQLiteDatabase database = db.getReadableDatabase();
//	    String query = "SELECT * FROM Produkte";
//	    return database.rawQuery(query, null);
//	}
//
//	public static Cursor fetchChildren(String name) 
//	{
//		SQLiteDatabase database = db.getReadableDatabase();
//	    String query = "SELECT * FROM Produkte WHERE Produkt = '" + name + "'";
//	    return database.rawQuery(query, null);
//	}
	
	public static void addProduct(Product product)
	{
		/* Referenz der Datenbank */
		
		SQLiteDatabase database = db.getWritableDatabase();
		
		/* Befüllen von value mit den Werten */
		
		ContentValues value = new ContentValues();
		value.put(Product.PRODUCT_DB_NAME, product.getProduct_name());
		value.put(Product.PRODUCT_DB_DESCRIPTION, product.getProduct_description());
		value.put(Product.PRODUCT_DB_PRICE, product.getProduct_price());
		 
		/* Einfügen einer neuen Zeile */
		
		database.insert(SQLiteDatabaseHandler.DATABASE_NAME, null, value);
	}

	public static ArrayList<Product> getProduct(String name /* ... */)
	{
		SQLiteDatabase database = db.getReadableDatabase();
		ArrayList<Product> results = new ArrayList<Product>();
		
		Cursor cursor = database.rawQuery("select Produkt,Beschreibung,Preis from Produkte where Produkt like '%"+name+"%'",null);
		if (cursor != null) 
		{
    		if  (cursor.moveToFirst()) 
    		{
    			do 
    			{
    				Product product = new Product(cursor.getString(cursor.getColumnIndex("Produkt"))
    				, cursor.getString(cursor.getColumnIndex("Beschreibung"))
    				, Double.parseDouble(cursor.getString(cursor.getColumnIndex("Preis"))));
    				results.add(product);
    			}while (cursor.moveToNext());
    		} 
    	}
		return results;
	}
	
	public static ArrayList<Product> getAllProducts()
	{
		SQLiteDatabase database = db.getReadableDatabase();
		ArrayList<Product> results = new ArrayList<Product>();
		
		Cursor cursor = database.rawQuery("SELECT Produkt,Beschreibung,Preis FROM PRODUKTE",null);
		if (cursor != null) 
		{
    		if  (cursor.moveToFirst()) 
    		{
    			do 
    			{
    				Product product = new Product(cursor.getString(cursor.getColumnIndex("Produkt"))
    				, cursor.getString(cursor.getColumnIndex("Beschreibung"))
    				, Double.parseDouble(cursor.getString(cursor.getColumnIndex("Preis"))));
    				results.add(product);
    			}while (cursor.moveToNext());
    		} 
    	}
		return results;
	}
	
	public static void removeEntry(Product product)
	{
		SQLiteDatabase database = db.getReadableDatabase();
		database.delete(SQLiteDatabaseHandler.DATABASE_NAME,SQLiteDatabaseHandler.PRODUCT_NAME+"='"+product.getProduct_name()+"'",null);
	}
}
