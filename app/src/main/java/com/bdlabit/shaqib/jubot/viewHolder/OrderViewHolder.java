package com.bdlabit.shaqib.jubot.viewHolder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bdlabit.shaqib.jubot.R;
import com.bdlabit.shaqib.jubot.interfacee.ItemClickListener;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView orderID, phoneNO, address,dAndT, total;

    private ItemClickListener itemClickListener;
    public OrderViewHolder(View itemView) {
        super(itemView);

        orderID = itemView.findViewById(R.id.order_id);
        phoneNO = itemView.findViewById(R.id.phone);
        address = itemView.findViewById(R.id.address);
        dAndT = itemView.findViewById(R.id.dAndT);
        total = itemView.findViewById(R.id.total);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
