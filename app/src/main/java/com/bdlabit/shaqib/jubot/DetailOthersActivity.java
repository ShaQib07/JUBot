package com.bdlabit.shaqib.jubot;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.bdlabit.shaqib.jubot.Database.Database;
import com.bdlabit.shaqib.jubot.Model.Food;
import com.bdlabit.shaqib.jubot.Model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DetailOthersActivity extends AppCompatActivity {
    TextView foodPrice, desc;
    ImageView foodImage;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String foodName = "";

    FirebaseDatabase database;
    DatabaseReference others;

    Database cartDb;

    Food currentFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH");
        String time = ""+format.format(calendar.getTime());
        final int t = Integer.parseInt(time);

        cartDb = new Database(getBaseContext());

        database = FirebaseDatabase.getInstance();
        others = database.getReference("Others");

        foodPrice = findViewById(R.id.foodPrice);
        desc = findViewById(R.id.description);
        foodImage = findViewById(R.id.imgFood);

        btnCart = findViewById(R.id.btnCart);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (12 < t && t < 15){
                    Toast.makeText(DetailOthersActivity.this, "Service not available at this moment.\nOur next service will start from 03:00 PM", Toast.LENGTH_LONG).show();
                } else if (19 < t && t < 23){
                    Toast.makeText(DetailOthersActivity.this, "Service not available at this moment.\nOur next service will start 11:00 PM", Toast.LENGTH_LONG).show();
                } else{
                    boolean check = cartDb.checkCart(currentFood.getName());
                    if (check){
                        Toast.makeText(DetailOthersActivity.this, "ALREADY ADDED", Toast.LENGTH_SHORT).show();
                    } else {
                        cartDb.addToCart(new Order(
                                currentFood.getName(),
                                numberButton.getNumber(),
                                currentFood.getPrice_tk()));

                        Toast.makeText(DetailOthersActivity.this, "||ADDED TO CART||", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        numberButton = findViewById(R.id.number_button);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        if (getIntent() != null){
            foodName = getIntent().getStringExtra("FoodName");
        }
        if (!foodName.isEmpty()){
            getDetailFood(foodName);
        }



    }

    private void getDetailFood(final String foodName) {
        others.child(foodName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentFood = dataSnapshot.getValue(Food.class);

//                byte[] decodeString = Base64.decode(currentFood.getImageLink(), Base64.DEFAULT);
//                Bitmap decoded = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
//                foodImage.setImageBitmap(decoded);

                Picasso.with(getBaseContext()).load(currentFood.getImageLink())
                        .into(foodImage);

                collapsingToolbarLayout.setTitle(currentFood.getName());

                foodPrice.setText(currentFood.getPrice_tk()+"TK");
                desc.setText("Best quality food, least possible cost...");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
