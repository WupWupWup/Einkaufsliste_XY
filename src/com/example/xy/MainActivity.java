package com.example.xy;

import com.database.xy.SQLiteDatabaseQueryHandler;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public class MainActivity extends ActionBarActivity
{
	
	@SuppressWarnings("unused")
	private SQLiteDatabaseQueryHandler databaseQueryHandler;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_forward_back, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		databaseQueryHandler = new SQLiteDatabaseQueryHandler(this);
	}
	
	public void loadList(View v)
	{
		Intent intent = new Intent(this, LoadListActivity.class);
		startActivity(intent);
	}
	
	public void browseDatabase(View v)
	{
		Intent intent = new Intent(this, BrowseDatabaseActivity.class);
		startActivity(intent);
	}
}
