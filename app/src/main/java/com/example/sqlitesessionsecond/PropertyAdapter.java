package com.example.sqlitesessionsecond;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>{
    private final ItemClick itemClick;
    private Context context;
    private List<Property> itemList;

    public PropertyAdapter(ItemClick itemClick, Context context, List<Property> itemList) {
        this.itemClick = itemClick;
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.property_list_item, parent, false);
        return new PropertyViewHolder(view,itemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyViewHolder holder, int position) {
        holder.img_logo.setImageBitmap(itemList.get(position).getImage());
        holder.date.setText(itemList.get(position).getDate());
        holder.price.setText(itemList.get(position).getPrice());
        holder.rule.setText(itemList.get(position).getRule());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class PropertyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_logo;
        TextView date,price,rule;
        public PropertyViewHolder(@NonNull View itemView, ItemClick itemClick) {
            super(itemView);
            img_logo = itemView.findViewById(R.id.img);
            date = itemView.findViewById(R.id.txt_property_date);
            price = itemView.findViewById(R.id.txt_price);
            rule = itemView.findViewById(R.id.txt_rules);

        }
    }
}
