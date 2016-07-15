package com.target.z00195z.frameworktest;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class CustomItemAdapter extends ArrayAdapter<Item>{
    public CustomItemAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView itemTitle = (TextView) convertView.findViewById(R.id.itemTitle);
        TextView itemPrice = (TextView) convertView.findViewById(R.id.itemPrice);
        CircleImageView imgView = (CircleImageView) convertView.findViewById(R.id.itemImage);


        Picasso.with(getContext()).load(item.imgUrl).into(imgView);
        itemTitle.setText(item.title);
        itemPrice.setText(item.price);

        return convertView;
    }
}
