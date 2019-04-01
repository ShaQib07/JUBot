package com.bdlabit.shaqib.jubot;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bdlabit.shaqib.jubot.Common.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class SignupActivity extends AppCompatActivity {


    TextView contBtn;
    EditText phoneNo, password;
    String phoneNumber, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        phoneNo = findViewById(R.id.et_phone);
        password = findViewById(R.id.et_pass);
        contBtn = findViewById(R.id.btn_continue);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        Paper.init(this);

        contBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber = "+88" + phoneNo.getText().toString();
                pass = password.getText().toString();

                if(phoneNumber.length() != 14){
                    Toast.makeText(SignupActivity.this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
                    phoneNo.requestFocus();
                }
                else if (pass.isEmpty() || pass.length()<8){
                    Toast.makeText(SignupActivity.this, "Your password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                }

                else if (Common.isConnectedToInternet(getBaseContext())) {

                    Paper.book().write(Common.phn, phoneNo.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if (dataSnapshot.child(phoneNumber).exists()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(SignupActivity.this, "Number already registered", Toast.LENGTH_SHORT).show();
                                } else {
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(SignupActivity.this,Verification.class);
                                    intent.putExtra("phoneNumber",phoneNumber);
                                    intent.putExtra("password",pass);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    Toast.makeText(SignupActivity.this, "Please check your internet connection and try again...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}