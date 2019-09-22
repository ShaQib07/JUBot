package com.bdlabit.shaqib.jubot;

import android.content.DialogInterface;
import android.graphics.Color;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bdlabit.shaqib.jubot.Common.Common;
import com.bdlabit.shaqib.jubot.Database.Database;
import com.bdlabit.shaqib.jubot.Model.Order;
import com.bdlabit.shaqib.jubot.Model.Request;
import com.bdlabit.shaqib.jubot.interfacee.RecyclerItemHelperListener;
import com.bdlabit.shaqib.jubot.viewHolder.CartAdapter;
import com.bdlabit.shaqib.jubot.viewHolder.CartViewHolder;
import com.bdlabit.shaqib.jubot.viewHolder.RecyclerItemTouchHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CartActivity extends AppCompatActivity implements RecyclerItemHelperListener {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    public TextView txtTotalPrice, btnPlace, txtEmptyCart, txtServiceCharge, txtRemoveCart;
    EditText etAddress, etComment;
    int total = 0;
    int serviceCharge = 5;
    String dAndT;
    ImageButton backBtn;

    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;

    RelativeLayout rootLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rootLayout = findViewById(R.id.rootLayout);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Request");

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy h:mm a");
        dAndT = format.format(calendar.getTime());

        recyclerView = findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        txtTotalPrice = findViewById(R.id.total);
        txtEmptyCart = findViewById(R.id.emptyCart);
        txtServiceCharge = findViewById(R.id.serviceCharge);
        txtRemoveCart = findViewById(R.id.removeCart);
        btnPlace = findViewById(R.id.btnPlaceOrder);
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cart.size()>0)
                    showAlertDialog();
                else
                    Toast.makeText(CartActivity.this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
            }
        });

        loadListFood();

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartActivity.this);
        LayoutInflater inflater = CartActivity.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);
        alertDialog.setView(view);
        alertDialog.setTitle("One more step!");
        alertDialog.setMessage("Enter your address please or set up your profile");

        etAddress = view.findViewById(R.id.et_add);
        etComment = view.findViewById(R.id.et_comment);
        etAddress.setText(Common.currentUser.getHall()+"\n"+
                        Common.currentUser.getRoom());
        etAddress.setSelection(etAddress.getText().length());
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    Request request = new Request(
                            Common.currentUser.getPhone(),
                            Common.currentUser.getName(),
                            etAddress.getText().toString(),
                            etComment.getText().toString(),
                            txtTotalPrice.getText().toString()+"+"+txtServiceCharge.getText().toString()+" service charge",
                            dAndT,
                            "Pending",
                            cart
                    );

                    String orderNumber = String.valueOf(System.currentTimeMillis());
                    requests.child(orderNumber).setValue(request);

                    new Database(getBaseContext()).cleanCart();
                    showSubmitted();
                }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();


    }

    private void showSubmitted() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartActivity.this);
        alertDialog.setTitle("DONE!")
                    .setMessage("ORDER SUBMITTED\nTHANKS FOR ORDERING")
                    .setCancelable(false)
                    .setIcon(R.drawable.ic_done_black_24dp)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .show();

    }

    private int getServiceCharge(int sum) {
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

    private void loadListFood() {
        total = 0;
        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart,this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        txtEmptyCart.setVisibility(View.INVISIBLE);
        for (Order order:cart){
            total += (Integer.parseInt(order.getFoodPrice()))*(Integer.parseInt(order.getFoodQuantity()));
            txtTotalPrice.setText(total+"TK");
            txtServiceCharge.setText(getServiceCharge(total)+"TK");
        }

        if (cart.isEmpty()) {
            txtTotalPrice.setText("0TK");
            txtEmptyCart.setVisibility(View.VISIBLE);
            txtRemoveCart.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CartViewHolder){
            String name = ((CartAdapter)recyclerView.getAdapter()).getItem(viewHolder.getAdapterPosition()).getFoodName();

            final Order deleteItem = ((CartAdapter)recyclerView.getAdapter()).getItem(viewHolder.getAdapterPosition());
            final int deleteIndex = viewHolder.getAdapterPosition();

            adapter.removeItem(deleteIndex);
            new Database(getBaseContext()).removeFromCart(deleteItem.getFoodName());

            loadListFood();

            Snackbar snackbar = Snackbar.make(rootLayout,name + " Removed from cart!",Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.restoreItem(deleteItem,deleteIndex);
                    new Database(getBaseContext()).addToCart(deleteItem);
                    loadListFood();
                    txtRemoveCart.setVisibility(View.VISIBLE);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }


}

