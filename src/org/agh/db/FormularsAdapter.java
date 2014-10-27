package org.agh.db;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.agh.wastemanagementapp.R;

import java.util.List;

public class FormularsAdapter extends ArrayAdapter<Formular> {
	private Activity context;
    private List<Formular> formulars;
    
    public FormularsAdapter(Activity context, List<Formular> formulars) {
        super(context, R.layout.db_formular_item, formulars);
        this.context = context;
        this.formulars = formulars;
    }

    static class ViewHolder{
    	public TextView tvFormular;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
    	View rowView = convertView;
    	ViewHolder viewHolder;
    	if (rowView == null){
    		LayoutInflater layoutInflater = context.getLayoutInflater();
        	rowView = layoutInflater.inflate(R.layout.db_formular_item, null, true);
        	viewHolder = new ViewHolder();
        	viewHolder.tvFormular = (TextView) rowView.findViewById(R.id.tvFormularId);
        	rowView.setTag(viewHolder);
    	}
    	else{
    		viewHolder = (ViewHolder) rowView.getTag(); 
    	}
    	
    	viewHolder.tvFormular.setText(String.valueOf(formulars.get(position).getId()));
    	
    	return rowView;
    }
}

