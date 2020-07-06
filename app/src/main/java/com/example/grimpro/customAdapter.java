package com.example.grimpro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class customAdapter extends BaseAdapter {

    private ArrayList<AppList> apps;
    private Context c;
    customAdapter(Context context, ArrayList<AppList> mApps){
        this.apps = mApps;
        this.c = context;
    }

    @Override
    public int getCount() {
        return apps.size();
    }

    @Override
    public Object getItem(int position) {
        return apps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater;
        View row = convertView;
        ViewHandler handler ;
        if(row == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row, parent, false);
            handler = new ViewHandler(row);
            row.setTag(handler);
        }
        else{
            handler = (ViewHandler) row.getTag();
        }
        handler.textView.setText(apps.get(position).getAppName());
        handler.imageView.setImageDrawable(apps.get(position).getIcon());
        handler.lockView.setImageResource(R.drawable.lock);
        if(apps.get(position).getLockStatus()){
            handler.lockView.setVisibility(View.VISIBLE);
        }
        else{
            handler.lockView.setVisibility(View.INVISIBLE);
        }
        return row;
    }

    class ViewHandler{
        ImageView imageView;
        TextView textView;
        ImageView lockView;

        ViewHandler(View v){
            imageView = v.findViewById(R.id.imageView);
            textView = v.findViewById(R.id.textView);
            lockView = v.findViewById(R.id.lock_symbol);
        }
    }
}