package com.listviewAdapter.xy;

import java.util.HashMap;
import java.util.List;

import com.example.xy.R;
 
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
 
public class ExpandableListViewAdapter extends BaseExpandableListAdapter 
{
 
    private Context _context;
    private List<String> _listDataHeader; 
    private HashMap<String, List<String>> _listDataChild;
 
    public ExpandableListViewAdapter(Context context, List<String> listDataHeader,
            HashMap<String, List<String>> listChildData) 
    {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }
 
    @Override
    public Object getChild(int groupPosition, int childPosititon) 
    {
    	/*
    	 * Gibt ein "Kind" innerhalb eines "Headers" zurück. Einkaufsliste 28.04 (Header) -> Produkte...(Kinder)
    	 */
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) 
    {
    	/*
    	 * Gibt die Position eines "Kindes" innerhalb des "Headers" zurück.
    	 */
        return childPosition;
    }
 
    @Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) 
    {
    	/*
    	 * Ausgabe der "Kinder" eines Headers und setzen derer Namen.
    	 */
        final String childText = (String) getChild(groupPosition, childPosition);
 
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.exp_list_item, null);
        }
 
        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);
 
        txtListChild.setText(childText);
        return convertView;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) 
    {
    	/*
    	 * Gibt Anzahl der "Kinder" eines "Headers" wieder.
    	 */
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }
 
    @Override
    public Object getGroup(int groupPosition) 
    {
    	/*
    	 * Gibt Header zurück
    	 */
        return this._listDataHeader.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() 
    {
    	/*
    	 *  Anzahl der vorhandenen Header
    	 */
        return this._listDataHeader.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) 
    {
    	/*
    	 * Position des Headers
    	 */
        return groupPosition;
    }
 
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) 
    {
    	/*
    	 * Zuständig für das Öffnen eines Headers und setzen des Namens des Headers.
    	 */
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) 
        {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.exp_list_group, null);
        }
 
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
 
        return convertView;
    }
 
    @Override
    public boolean hasStableIds() 
    {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) 
    {
        return true;
    }
}
