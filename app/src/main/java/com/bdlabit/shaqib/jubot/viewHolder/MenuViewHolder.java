package com.bdlabit.shaqib.jubot.viewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bdlabit.shaqib.jubot.R;
import com.bdlabit.shaqib.jubot.interfacee.ItemClickListener;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

    public TextView foodName, foodPrice;
    public ImageView foodImage;
    private ItemClickListener itemClickListener;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);

        foodName = itemView.findViewById(R.id.tvName);
        foodPrice = itemView.findViewById(R.id.tvPrice);

        foodImage = itemView.findViewById(R.id.img);


        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
