package com.bdlabit.shaqib.jubot;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.bdlabit.shaqib.jubot.Model.Order;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FoodListActivity extends AppCompatActivity {

    ImageButton backBtn;
    String requestID = "";
    FirebaseDatabase database;
    DatabaseReference request;
    ListView listView;
    FirebaseListAdapter<Order> adapter;
    TextView mFoodName, mFoodPrice, mFoodQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        database = FirebaseDatabase.getInstance();
        listView = findViewById(R.id.listFood);


        if (getIntent() != null){
            requestID = getIntent().getStringExtra("RequestID");

            request = database.getReference("Request").child(requestID).child("foods");
        }
        if (!requestID.isEmpty()){
            getDetailOrder();

        }
    }

    private void getDetailOrder() {
        request.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                FirebaseListOptions<Order> options = new FirebaseListOptions.Builder<Order>()
                        .setLayout(R.layout.food_item)
                        .setQuery(request, Order.class)
                        .build();

                adapter = new FirebaseListAdapter<Order>(options) {
                    @Override
                    protected void populateView(@NonNull View v, @NonNull Order model, int position) {
                        mFoodName = v.findViewById(R.id.foodName);
                        mFoodPrice = v.findViewById(R.id.foodPrice);
                        mFoodQuantity = v.findViewById(R.id.foodQuantity);

                        mFoodName.setText(model.getFoodName());
                        mFoodPrice.setText(model.getFoodPrice() +"tk");
                        mFoodQuantity.setText(model.getFoodQuantity() +"pc(s)");
                    }
                };

                listView.setAdapter(adapter);
                adapter.startListening();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
