package com.database.xy;

import com.example.xy.R;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper
{
	public static final String DATABASE_NAME ="Produkte";
	public static final int DATABASE_VERSION = 2;
	
	/* Spaltennamen der Datenbank */
	
	public static final String id ="P_ID";
	public static final String PRODUCT_NAME = "Produkt";
	public static final String PRODUCT_DESCRIPTION ="Beschreibung";
	public static final String PRODUCT_PRICE ="Preis";
	
	private Context context;
	public SQLiteDatabaseHandler(Context context) 
	{
		 super(context, 
			   context.getResources().getString(R.string.dbname), 
			   null, 
			   Integer.parseInt(context.getResources().getString(R.string.version)));
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase database) 
	{
		for (String sql: context.getResources().getStringArray(R.array.create))
			database.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) 
	{
		for (String sql: context.getResources().getStringArray(R.array.drop))
			database.execSQL(sql);
		onCreate(database);
	}
}
