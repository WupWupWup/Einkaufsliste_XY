package com.example.xy;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.database.xy.Product;
import com.listviewAdapter.xy.ExpandableListViewAdapter;
import com.xml.xy.XMLHandler;

import android.support.v7.app.ActionBarActivity;
import android.widget.ExpandableListView;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

public class LoadListActivity extends ActionBarActivity 
{
	private ExpandableListViewAdapter listAdapter;
	private ExpandableListView expListView;
	private List<String> listDataHeader;
	private HashMap<String, List<String>> listDataChild;
	private XMLHandler handler = new XMLHandler();
	private String[] filenames;
	private int exp_count;
	private Context context;
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loadlist_activity);
		
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        context = getApplicationContext();
        prepareListData();
        listAdapter = new ExpandableListViewAdapter(context, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
	}
	
	private void prepareListData() 
	{
		new getExpListViewData().execute();
    }
	
	private class getExpListViewData extends AsyncTask<Void, Void,Void>
	{
		/*
		 * Ruft alle Dateinamen im Ordner Einkaufsliste auf. Liest die XML Dateien. Speichert den Namen der Datei und unter diesem die Produkte ab
		 */
	     protected Void doInBackground(Void... v) 
	     {
	    	 if(filenames != null)
		    	 for(int i = 0; i<filenames.length;i++)
		         {
		         	listDataHeader.add(filenames[i]);
		         	List<String> product = new ArrayList<String>();
		         	List<Product> list = new ArrayList<Product>();
		         	
		         	list = handler.readXMLFile(filenames[i]);
		         	
		         	for(int k = 0; k<list.size();k++)
		         	{
		         		product.add(list.get(k).getProduct_name());
		         	}
		         	listDataChild.put(listDataHeader.get(exp_count), product);
		         	exp_count = exp_count + 1;
		         }
	    	 return null;
	     }

	     protected void onPreExecute()
	     {
	    	 listDataHeader = new ArrayList<String>();
	         listDataChild = new HashMap<String, List<String>>();
	         exp_count = 0;
	         filenames = handler.getFileNames();
	     }
	 }
}
