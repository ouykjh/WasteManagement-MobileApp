package org.agh.db;

import java.util.List;

import org.agh.wastemanagementapp.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class RoutesAdapter extends ArrayAdapter<Route> {
	private Activity context;
    private List<Route> routes;
    
    public RoutesAdapter(Activity context, List<Route> routes) {
        super(context, R.layout.db_route_item, routes);
        this.context = context;
        this.routes = routes;
    }

    static class ViewHolder{
    	public TextView tvRoute;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
    	View rowView = convertView;
    	ViewHolder viewHolder;
    	if (rowView == null){
    		LayoutInflater layoutInflater = context.getLayoutInflater();
        	rowView = layoutInflater.inflate(R.layout.db_route_item, null, true);
        	viewHolder = new ViewHolder();
        	viewHolder.tvRoute = (TextView) rowView.findViewById(R.id.tvRouteName);
        	rowView.setTag(viewHolder);
    	}
    	else{
    		viewHolder = (ViewHolder) rowView.getTag(); 
    	}
    	
    	viewHolder.tvRoute.setText(routes.get(position).getName());

    	return rowView;
    }
}
