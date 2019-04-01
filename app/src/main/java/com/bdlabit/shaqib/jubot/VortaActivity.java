package com.bdlabit.shaqib.jubot;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.andremion.counterfab.CounterFab;
import com.bdlabit.shaqib.jubot.Database.Database;
import com.bdlabit.shaqib.jubot.Model.Food;
import com.bdlabit.shaqib.jubot.interfacee.ItemClickListener;
import com.bdlabit.shaqib.jubot.viewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class VortaActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference vorta;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    CounterFab fab;
    FirebaseRecyclerAdapter<Food,MenuViewHolder> adapter;
    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food);

        database = FirebaseDatabase.getInstance();
        vorta = database.getReference("Vorta");

        recyclerView = findViewById(R.id.list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadFOod();

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VortaActivity.this,CartActivity.class));
            }
        });

        fab.setCount(new Database(this).getCountCart());

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void loadFOod() {
        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(vorta, Food.class).build();

        adapter = new FirebaseRecyclerAdapter<Food, MenuViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder holder, int position, @NonNull final Food model) {
                holder.foodName.setText(model.getName());
                holder.foodPrice.setText(model.getPrice_tk() + "TK");

//                byte[] decodeString = Base64.decode(model.getImageLink(), Base64.DEFAULT);
//                Bitmap decoded = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
//                holder.foodImage.setImageBitmap(decoded);

                Picasso.with(getBaseContext()).load(model.getImageLink())
                        .into(holder.foodImage);

                final Food clickItem = model;

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent detail = new Intent(VortaActivity.this,DetailVortaActivity.class);
                        detail.putExtra("FoodName",adapter.getRef(position).getKey());
                        startActivity(detail);}
                });
            }

            @NonNull
            @Override
            public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View itemView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.menu_item, viewGroup, false);
                return new MenuViewHolder(itemView);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        fab.setCount(new Database(this).getCountCart());
    }

}
