package com.example.sqlitesessionsecond;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private final ItemClick itemClick;
    private Context context;
    private List<Item> itemList;

    public ItemAdapter(ItemClick itemClick, Context context, List<Item> itemList) {
        this.itemClick = itemClick;
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listing_list_item, parent, false);
        return new ItemViewHolder(view,itemClick);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item item = itemList.get(position);

        // Set the image, title, and date values for each item
        holder.imageView.setImageBitmap(item.getImage());
        holder.titleTextView.setText(item.getTitle());
        holder.dateTextView.setText(item.getDate());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView titleTextView;
        TextView dateTextView;

        public ItemViewHolder(View itemView,ItemClick itemClick) {
            super(itemView);
            imageView = itemView.findViewById(R.id.btn_img);
            titleTextView = itemView.findViewById(R.id.txt_title);
            dateTextView = itemView.findViewById(R.id.txt_datereal);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClick!=null){
                        int pos = getAdapterPosition();

                        if(pos!= RecyclerView.NO_POSITION){
                            itemClick.itemClick(pos);
                        }
                    }
                }
            });
        }
    }
}