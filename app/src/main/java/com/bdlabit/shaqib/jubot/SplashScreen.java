package com.bdlabit.shaqib.jubot;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bdlabit.shaqib.jubot.Common.Common;
import com.bdlabit.shaqib.jubot.Model.User;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.fabric.sdk.android.Fabric;
import io.paperdb.Paper;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash_screen);

        Paper.init(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String phone = Paper.book().read(Common.phn);
                if (phone != null){
                    if (!phone.isEmpty())
                        login(phone);
                }
                else {
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                    finish();

                }    }
        },1000);
    }


    private void login(final String phone) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        if (Common.isConnectedToInternet(getBaseContext())) {

            table_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.child(phone).exists()) {
                        User user = dataSnapshot.child(phone).getValue(User.class);
                        user.setPhone(phone);
                        Common.currentUser = user;

                        startActivity(new Intent(SplashScreen.this, TabActivity.class));
                        finish();
                    }
                }



                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else{
            Toast.makeText(SplashScreen.this, "Please check your internet connection and try again...", Toast.LENGTH_SHORT).show();
        }
    }
}