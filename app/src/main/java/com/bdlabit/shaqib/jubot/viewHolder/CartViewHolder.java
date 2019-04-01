package com.bdlabit.shaqib.jubot.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.bdlabit.shaqib.jubot.R;
import com.bdlabit.shaqib.jubot.interfacee.ItemClickListener;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
        View.OnCreateContextMenuListener{
    public TextView txtCartName, txtCartPrice;

    public ElegantNumberButton numberQuantity;

    public RelativeLayout viewBackground;
    public LinearLayout viewForeground;

    private ItemClickListener itemClickListener;

    public void setTxtCartName(TextView txtCartName) {
        this.txtCartName = txtCartName;
    }

    public CartViewHolder(View itemView) {
        super(itemView);
        txtCartName = itemView.findViewById(R.id.cart_item_name);
        txtCartPrice = itemView.findViewById(R.id.cart_item_price);

        numberQuantity = itemView.findViewById(R.id.btn_quantity);

        viewBackground = itemView.findViewById(R.id.view_background);
        viewForeground = itemView.findViewById(R.id.view_foreground);

        itemView.setOnCreateContextMenuListener(this);


    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        menu.setHeaderTitle("Delete from cart");
        menu.setHeaderIcon(R.drawable.ic_delete_sweep_black_24dp);
        menu.add(0,0,getAdapterPosition(),"Delete");
    }


}

