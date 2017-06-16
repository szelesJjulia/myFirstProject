package com.ebookfrenzy.szelesjulide.practiceprojectgooglemaplist.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ebookfrenzy.szelesjulide.practiceprojectgooglemaplist.R;

import java.util.ArrayList;

/**
 * Created by szeles.julide on 2017/06/13.
 */

public class CostumAdapterForItem extends ArrayAdapter<ItemTop> {

    private ArrayList<ItemTop> dataSet;
    Context myContext;

    private static class ViewHolder{
        TextView itemTextView;
        CheckBox checkBox;
    }

    public CostumAdapterForItem(ArrayList<ItemTop> list, Context context){
        super(context, R.layout.raw_item_item,list);

        this.dataSet = list;
        this.myContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //
        ItemTop itemTop = getItem(position);
        ViewHolder viewHolder;

        final View result;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.raw_item_item,parent,false);
            viewHolder.itemTextView = (TextView)convertView.findViewById(R.id.textViewItemName);
            viewHolder.checkBox = (CheckBox)convertView.findViewById(R.id.checkBoxAdding);

            result = convertView;
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
            result = convertView;
        }

        viewHolder.itemTextView.setText(itemTop.getItemName().toString());
        viewHolder.checkBox.setChecked(itemTop.isChecked());
        String tagString = itemTop.getItemName().toString();

        viewHolder.checkBox.setTag(tagString);

        return convertView;
    }
}
