package com.bdlabit.shaqib.jubot;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bdlabit.shaqib.jubot.Common.Common;
import com.bdlabit.shaqib.jubot.Model.Request;
import com.bdlabit.shaqib.jubot.interfacee.ItemClickListener;
import com.bdlabit.shaqib.jubot.viewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MyOrdersActivity extends AppCompatActivity {

    ImageButton backBtn;
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    TextView textView;

    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        textView = findViewById(R.id.txtOrders);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Request");

        recyclerView = findViewById(R.id.listOrder);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrders(Common.currentUser.getPhone());
    }

    private void loadOrders(String phone) {
        Query getOrderByUser = requests.orderByChild("phone").equalTo(phone);
        FirebaseRecyclerOptions<Request> orderOptions = new FirebaseRecyclerOptions.Builder<Request>()
                .setQuery(getOrderByUser,Request.class).build();

        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(orderOptions) {
            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder viewHolder, int position, @NonNull Request model) {
                viewHolder.orderID.setText(adapter.getRef(position).getKey());
                viewHolder.phoneNO.setText(model.getPhone());
                viewHolder.address.setText(model.getAddress());
                viewHolder.dAndT.setText(model.getDateAndTime());
                viewHolder.total.setText(model.getTotal());

                final Request clickItem = model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foodList = new Intent(MyOrdersActivity.this,FoodListActivity.class);
                        foodList.putExtra("RequestID",adapter.getRef(position).getKey());
                        startActivity(foodList);}
                });
            }

            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.order_layout,parent,false);
                return new OrderViewHolder(itemView);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);




    }
}
