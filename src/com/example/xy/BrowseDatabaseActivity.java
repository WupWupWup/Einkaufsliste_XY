package com.example.xy;

import java.util.ArrayList;
import java.util.List;
import com.database.xy.Product;
import com.database.xy.SQLiteDatabaseQueryHandler;

import com.listviewAdapter.xy.ListViewAdapter;
import com.xml.xy.XMLHandler;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

public class BrowseDatabaseActivity extends Activity
{
	private ListViewAdapter adapter;
	private ListViewAdapter search_result_adapter;
	private List<Product> shoppinglist = new ArrayList<Product>();
	private EditText et_product_name;
	private EditText et_product_description;
	private EditText et_product_price;
	private EditText et_product_filename;
	private EditText et_search_field;
	private ListView lv;
	private Context context;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browsedatabase_activity);
		lv = (ListView)findViewById(android.R.id.list);
		et_search_field = (EditText) findViewById(R.id.et_search);
		
		et_search_field.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

			@Override
			public void afterTextChanged(Editable s) {
				if(!s.toString().isEmpty())
					new SearchDatabaseEntry().execute(s.toString());
				else
					lv.setAdapter(adapter);
			}
        });
		context = this;
		new GetListViewData().execute(context);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflate = getMenuInflater();
		inflate.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu( menu );
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
	    switch (item.getItemId())
	    {
	        case R.id.action_settings:
	        	show_dialog();
	            return true;
	        case R.id.save_list:
	        	if (!shoppinglist.isEmpty())
					show_save_dialog();
				else
					Toast.makeText(getApplicationContext(), "Die Liste ist leer!",
							   Toast.LENGTH_LONG).show();
	        	return true;
	        default:
	            return false;
	    }
	}
	
	public void resetSearch(View view)
	{
		lv.setAdapter(adapter);
		et_search_field.setText(null);
	}
	
	private void show_dialog()
	{
		/*
		 * Öffnet den Dialog um ein neues Produkt aufzunehmen und ruft Funktion zum hinzufügen des Produktes auf
		 */
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.add_dialog);
		dialog.setTitle("Eintrag hinzufügen");
		
		et_product_name = (EditText) dialog.findViewById(R.id.et_dialog_product_name);
		et_product_description = (EditText) dialog.findViewById(R.id.et_dialog_product_description);
		et_product_price = (EditText) dialog.findViewById(R.id.et_dialog_product_price);
		
		Button dialogButtonOk = (Button) dialog.findViewById(R.id.btn_dialog_ok);
		dialogButtonOk.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				if(!et_product_name.getText().toString().isEmpty()
					&& !et_product_description.getText().toString().isEmpty()
					&& !et_product_price.getText().toString().isEmpty())
					{
						Product product = new Product(
								(et_product_name.getText().toString()),
								(et_product_description.getText().toString()),
								(Double.parseDouble(et_product_price.getText().toString())));
						new AddDatabaseEntry().execute(product);
						dialog.dismiss();
					}
				else
					Toast.makeText(getApplicationContext(), "Feld nicht korrekt ausgefüllt!",
							   Toast.LENGTH_LONG).show();		
			}
		});
		
		Button dialogButtonDismiss = (Button) dialog.findViewById(R.id.btn_dialog_cancel);
		dialogButtonDismiss.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
	private void show_save_dialog()
	{
		/*
		 * Öffnet den Savedialog um eine neue Einkaufsliste abzuspeichern. (Fordert Name der Liste an)
		 */
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.save_dialog);
		dialog.setTitle("Name der Liste");
		
		et_product_filename = (EditText) dialog.findViewById(R.id.et_dialog_product_filename);
		
		Button dialogButtonOk = (Button) dialog.findViewById(R.id.btn_dialog_ok);
		dialogButtonOk.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				if(!et_product_filename.getText().toString().isEmpty())
				{
					saveList(et_product_filename.getText().toString());
					dialog.dismiss();
				}
				else
					Toast.makeText(getApplicationContext(), "Filename ist leer!",
							   Toast.LENGTH_LONG).show();		
			}
		});
		
		Button dialogButtonDismiss = (Button) dialog.findViewById(R.id.btn_dialog_cancel);
		dialogButtonDismiss.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
	
	public void removeDatabaseItem(final View view) 
	{
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.delete_dialog);
		dialog.setTitle("Produkt löschen...");
		
		Button dialogButtonOk = (Button) dialog.findViewById(R.id.btn_dialog_ok);
		dialogButtonOk.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				new RemoveDatabaseEntry().execute(view);
				dialog.dismiss();
			}
		});
		
		Button dialogButtonDismiss = (Button) dialog.findViewById(R.id.btn_dialog_cancel);
		dialogButtonDismiss.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
	
	public void addToList(View v)
	{
		/*
		 * Fügt Produkt der Einkaufsliste hinzu (Liste<Produkt>)
		 */
		Product itemToAdd = (Product)v.getTag();
		shoppinglist.add(itemToAdd);
		Toast.makeText(getApplicationContext(), "Produkt zur Einkaufsliste hinzugefügt",
				   Toast.LENGTH_LONG).show();	
	}
	
	public void saveList(String dataName)
	{
		/*
		 * Ruft XMLHandler auf um Daten abzuspeichern
		 */
		if(!shoppinglist.isEmpty())
		{
			XMLHandler handler = new XMLHandler(dataName,(ArrayList<Product>)shoppinglist);
			handler.generateXMLFile();
		}else
			Toast.makeText(getApplicationContext(), "Die Einkaufsliste ist leer!",
					   Toast.LENGTH_LONG).show();	
	}
	
	private class GetListViewData extends AsyncTask<Context, Void, ListViewAdapter>
	{
		/*
		 * Lädt beim erstellen der Activity die Daten aus der Datenbank und fügt sie der ListView hinzu
		 */
	     protected ListViewAdapter doInBackground(Context... context) 
	     {
	    	adapter = new ListViewAdapter(context[0], R.layout.group, SQLiteDatabaseQueryHandler.getAllProducts());
	    	return adapter;
	     }

	     protected void onPostExecute(ListViewAdapter result)
	     {
		 	lv.setAdapter(adapter);	
	     }
	 }
	
	private class AddDatabaseEntry extends AsyncTask<Product, Void, Product>
	{
	     protected Product doInBackground(Product... product) 
	     {
	    	 /*
	    	  * Fügt das Produkt zur Datenbank hinzu
	    	  */
	    	 SQLiteDatabaseQueryHandler.addProduct(product[0]);
	    	 return product[0];
	     }

	     protected void onPostExecute(Product product)
	     {
	    	 /*
	    	  * Fügt das Produkt der ListView hinzu
	    	  */
	    	 adapter.add(product);
	    	 adapter.notifyDataSetChanged();
	     }
	 }
	
	private class RemoveDatabaseEntry extends AsyncTask<View, Void, Product>
	{
	     protected Product doInBackground(View... v) 
	     {
	    	 /*
	    	  * Löscht das Produkt aus der Datenbank
	    	  */
	    	Product itemToRemove = (Product)v[0].getTag();
	 		SQLiteDatabaseQueryHandler.removeEntry(itemToRemove);
	 		return itemToRemove;
	     }

	     protected void onPostExecute(Product itemToRemove)
	     {
	    	 /*
	    	  * Löscht das Produkt aus der ListView
	    	  */
	    	 adapter.remove(itemToRemove);
	    	 adapter.notifyDataSetChanged();
	     }
	 }
	
	private class SearchDatabaseEntry extends AsyncTask<String, Void, ListViewAdapter>
	{
	    protected ListViewAdapter doInBackground(String... name) 
	    {
	    	if(!name[0].isEmpty())
	    	{
		    	ArrayList<Product> result = SQLiteDatabaseQueryHandler.getProduct(name[0]);
		    	if(!result.isEmpty())
		    	{
			    	search_result_adapter = new ListViewAdapter(context, R.layout.group,result);
			 		return search_result_adapter;
		    	}
		    	else
		    		return adapter;
	    	}
	    	else
	    		return adapter;	
	    }

		protected void onPostExecute(ListViewAdapter search_)
	    {
			if(!search_.equals(adapter))
				lv.setAdapter(search_);
			else
				Toast.makeText(getApplicationContext(), "Produkt konnte nicht gefunden werden!",
						   Toast.LENGTH_LONG).show();		
	    }
	 }
}
