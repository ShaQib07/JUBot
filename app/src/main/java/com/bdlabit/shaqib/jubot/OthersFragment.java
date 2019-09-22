package com.bdlabit.shaqib.jubot;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bdlabit.shaqib.jubot.Model.Food;
import com.bdlabit.shaqib.jubot.interfacee.ItemClickListener;
import com.bdlabit.shaqib.jubot.viewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class OthersFragment extends Fragment {

    View view;
    FirebaseDatabase database;
    DatabaseReference others;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Food, MenuViewHolder> adapter;

    public OthersFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.food, container, false);

        database = FirebaseDatabase.getInstance();
        others = database.getReference("Others");

        recyclerView = view.findViewById(R.id.list);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        loadFOod();

        return view;
    }

    private void loadFOod() {
        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(others, Food.class).build();

        adapter = new FirebaseRecyclerAdapter<Food, MenuViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder holder, int position, @NonNull final Food model) {
                holder.foodName.setText(model.getName());
                holder.foodPrice.setText(model.getPrice_tk() + "TK");

                Picasso.with(getContext())
                        .load(model.getImageLink())
                        .into(holder.foodImage);

                final Food clickItem = model;

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent detail = new Intent(getActivity(),DetailOthersActivity.class);
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

}
