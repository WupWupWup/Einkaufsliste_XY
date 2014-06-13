package com.listviewAdapter.xy;

import java.util.ArrayList;
import java.util.List;

import com.database.xy.Product;
import com.example.xy.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class ListViewAdapter extends ArrayAdapter<Product> 
{

	private List<Product> items;
	private int layoutResourceId;
	private Context context;

	public ListViewAdapter(Context context, int layoutResourceId, ArrayList<Product> arrayList) 
	{
		super(context, layoutResourceId, arrayList);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.items = arrayList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View row = convertView;
		ProductHoler holder = null;

		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);

		holder = new ProductHoler();
		holder.product = items.get(position);
		holder.deleteButton = (ImageButton)row.findViewById(R.id.IBTN_delete);
		holder.deleteButton.setTag(holder.product);
		holder.addButton = (ImageButton)row.findViewById(R.id.IBTN_add);
		holder.addButton.setTag(holder.product);
		
		holder.name = (TextView)row.findViewById(R.id.tv_product_name);

		row.setTag(holder);

		setupItem(holder);
		return row;
	}

	private void setupItem(ProductHoler holder)
	{
		holder.name.setText(holder.product.getProduct_name());
	}

	public static class ProductHoler 
	{
		Product product;
		TextView name;
		ImageButton deleteButton;
		ImageButton editButton;
		ImageButton addButton;
	}
}
