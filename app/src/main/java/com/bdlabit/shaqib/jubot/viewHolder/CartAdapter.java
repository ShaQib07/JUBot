package com.bdlabit.shaqib.jubot.viewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.bdlabit.shaqib.jubot.CartActivity;
import com.bdlabit.shaqib.jubot.Database.Database;
import com.bdlabit.shaqib.jubot.Model.Order;
import com.bdlabit.shaqib.jubot.R;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Order> listData = new ArrayList<>();
    private CartActivity cartActivity;

    public CartAdapter(List<Order> listData, CartActivity cartActivity) {
        this.listData = listData;
        this.cartActivity = cartActivity;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(cartActivity);
        View view = inflater.inflate(R.layout.cart_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, final int position) {
        //TextDrawable drawable = TextDrawable.builder().buildRound(""+listData.get(position).getQuantity(),Color.BLACK);
        //holder.imgCartCount.setImageDrawable(drawable);
        holder.numberQuantity.setNumber(listData.get(position).getFoodQuantity());
        holder.numberQuantity.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Order order = listData.get(position);
                order.setFoodQuantity(String.valueOf(newValue));
                new Database(cartActivity).updateCart(order);

                int total = 0;

                List<Order> orders = new Database(cartActivity).getCarts();
                for (Order item:orders) {
                    total += (Integer.parseInt(item.getFoodPrice())) * (Integer.parseInt(item.getFoodQuantity()));
                    notifyDataSetChanged();
                    cartActivity.txtTotalPrice.setText(total + "TK");
                    cartActivity.txtServiceCharge.setText(getServiceCharge(total)+"TK");


                }
            }
        });
        int quantity = Integer.parseInt(holder.numberQuantity.getNumber());
        int price = (Integer.parseInt(listData.get(position).getFoodPrice()) * quantity); //(Integer.parseInt(listData.get(position).getQuantity())));;
        holder.txtCartPrice.setText(String.valueOf(price)+"TK");
        holder.txtCartName.setText(listData.get(position).getFoodName());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public Order getItem(int position){
        return listData.get(position);
    }

    public void removeItem(int position){
        listData.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Order item, int position){
        listData.add(position,item);
        notifyItemInserted(position);
    }

    private int getServiceCharge(int sum) {
        int serviceCharge;
        if (sum <= 100){
            serviceCharge = 5;
        } else if (sum <= 200){
            serviceCharge = 10;
        } else if (sum <= 300){
            serviceCharge = 15;
        } else if (sum <= 400){
            serviceCharge = 20;
        } else if (sum <= 500){
            serviceCharge = 25;
        } else if (sum <= 600){
            serviceCharge = 30;
        } else if (sum <= 700){
            serviceCharge = 35;
        } else {
            serviceCharge = 40;
        }
        return serviceCharge;
    }


}

