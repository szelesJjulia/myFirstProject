package com.ebookfrenzy.szelesjulide.practiceprojectgooglemaplist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by szeles.julide on 2017/06/13.
 */

public class CostumAdapter extends ArrayAdapter<String> {

    private ArrayList<String> dataSet;
    Context myContext;

    private static class ViewHolder{
        ImageView iconImage;
        TextView textView;

    }

    public CostumAdapter(ArrayList<String> data,Context context){
        super(context,R.layout.raw_item_category,data);
        this.dataSet = data;
        this.myContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String actualItem = getItem(position);
        ViewHolder viewHolder;

        final View result;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.raw_item_category,parent,false);
            viewHolder.textView = (TextView)convertView.findViewById(R.id.textViewCatgory);
            viewHolder.iconImage = (ImageView)convertView.findViewById(R.id.imageView);

            result = convertView;
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.textView.setText(actualItem);
        viewHolder.iconImage.setImageResource(android.R.drawable.sym_def_app_icon);
        return convertView;
    }
}
