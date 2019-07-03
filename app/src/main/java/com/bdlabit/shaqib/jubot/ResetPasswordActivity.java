package com.bdlabit.shaqib.jubot;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bdlabit.shaqib.jubot.Common.Common;
import com.bdlabit.shaqib.jubot.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class ResetPasswordActivity extends AppCompatActivity {

    TextView btnCont;
    EditText etNewPass, etConfirmPass;
    String phoneNumber, newPass, confirmPass;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_user = database.getReference("User");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Paper.init(this);

        btnCont = findViewById(R.id.btn_continue);
        etNewPass = findViewById(R.id.et_pass);
        etConfirmPass = findViewById(R.id.et_confirm_pass);
        phoneNumber = getIntent().getStringExtra("phoneNumber");





        btnCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newPass = etNewPass.getText().toString();
                confirmPass = etConfirmPass.getText().toString();
                if (newPass.isEmpty() || newPass.length()<8){
                    Toast.makeText(ResetPasswordActivity.this, "Your password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
                } else {
                    if (newPass.equals(confirmPass)){
                        table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if (dataSnapshot.child(phoneNumber).exists()) {
                                    User user = dataSnapshot.child(phoneNumber).getValue(User.class);
                                    user.setPhone(phoneNumber);
                                    Common.currentUser = user;

                                    Paper.book().write(Common.phn, phoneNumber);
                                    User overWriteUser = new User(phoneNumber,
                                            confirmPass,
                                            Common.currentUser.getName(),
                                            Common.currentUser.getDept(),
                                            Common.currentUser.getHall(),
                                            Common.currentUser.getRoom(),
                                            Common.currentUser.getBatch(),
                                            Common.currentUser.getEmail());
                                    Common.currentUser = overWriteUser;
                                    table_user.child(phoneNumber).setValue(overWriteUser);
                                    Intent intent = new Intent(ResetPasswordActivity.this, TabActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                    startActivity(intent);


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    } else {
                        Toast.makeText(ResetPasswordActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
