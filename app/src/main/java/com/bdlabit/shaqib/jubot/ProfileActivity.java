package com.bdlabit.shaqib.jubot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bdlabit.shaqib.jubot.Common.Common;
import com.bdlabit.shaqib.jubot.Model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    TextView tvDoneBtn;
    EditText etName, etDept, etBatch, etHall, etRoom, etMail;
    ImageButton backBtn;
    String phone, password, name, dept, hall, room, batch, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");


        etName = findViewById(R.id.et_name);
        etDept = findViewById(R.id.et_dept);
        etBatch = findViewById(R.id.et_batch);
        etHall = findViewById(R.id.et_hall);
        etRoom = findViewById(R.id.et_room);
        etMail = findViewById(R.id.et_email);
        tvDoneBtn = findViewById(R.id.btn_done);

        etName.setText(Common.currentUser.getName());
        etName.setSelection(etName.getText().length());

        etDept.setText(Common.currentUser.getDept());

        etBatch.setText(Common.currentUser.getBatch());

        etHall.setText(Common.currentUser.getHall());

        etRoom.setText(Common.currentUser.getRoom());

        etMail.setText(Common.currentUser.getEmail());

        tvDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = etName.getText().toString();
                dept = etDept.getText().toString();
                batch = etBatch.getText().toString();
                hall = etHall.getText().toString();
                room = etRoom.getText().toString();
                email = etMail.getText().toString();
                phone = Common.currentUser.getPhone();
                password = Common.currentUser.getPassword();
                if (!email.isEmpty()){
                    User user = new User(phone, password, name, dept, hall, room, batch, email);
                    Common.currentUser = user;
                    table_user.child(phone).setValue(user);

                    Toast.makeText(ProfileActivity.this, "Profile updated successfully...", Toast.LENGTH_SHORT).show();
                    finish();
                } else{
                    User user = new User(phone, password, name, dept, hall, room, batch);
                    Common.currentUser = user;
                    table_user.child(phone).setValue(user);

                    Toast.makeText(ProfileActivity.this, "Profile updated successfully...", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
