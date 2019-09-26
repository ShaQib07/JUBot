package com.bdlabit.shaqib.jubot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bdlabit.shaqib.jubot.Common.Common;
import com.bdlabit.shaqib.jubot.Model.Review;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReviewActivity extends AppCompatActivity {

    RadioGroup ans1, ans2, ans3, ans4;
    RadioButton radioButton;
    EditText  ans5;
    TextView submitBtn, cancelBtn;
    String name, phone,  ansOne, ansTwo, ansThree, ansFour, ansFive;
    int selectedId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reviews = database.getReference("Review");


        ans1 = findViewById(R.id.rad_ans1);
        ans2 = findViewById(R.id.rad_ans2);
        ans3 = findViewById(R.id.rad_ans3);
        ans4 = findViewById(R.id.rad_ans4);
        ans5 = findViewById(R.id.et_ans5);

        submitBtn = findViewById(R.id.btn_submit);
        cancelBtn = findViewById(R.id.btn_cancel);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReviewActivity.this, SplashScreen.class));
                finish();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.currentUser == null){
                    Toast.makeText(ReviewActivity.this, "Oops! you need to login first.", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    name = Common.currentUser.getName();
                    phone = Common.currentUser.getPhone();
                    //1st ans
                    selectedId = ans1.getCheckedRadioButtonId();
                    radioButton = findViewById(selectedId);
                    ansOne = radioButton.getText().toString();
                    //2nd ans
                    selectedId = ans2.getCheckedRadioButtonId();
                    radioButton = findViewById(selectedId);
                    ansTwo = radioButton.getText().toString();
                    //3rd ans
                    selectedId = ans3.getCheckedRadioButtonId();
                    radioButton = findViewById(selectedId);
                    ansThree = radioButton.getText().toString();
                    //4th ans
                    selectedId = ans4.getCheckedRadioButtonId();
                    radioButton = findViewById(selectedId);
                    ansFour = radioButton.getText().toString();
                    //5th ans
                    ansFive = ans5.getText().toString();

                    Review review = new Review(name, phone, ansOne, ansTwo, ansThree, ansFour, ansFive);
                    reviews.child(phone).setValue(review);
                    Toast.makeText(ReviewActivity.this, "||Feedback submitted||\nThanks for reviewing.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ReviewActivity.this, TabActivity.class));
                    finish();
                }


            }
        });

    }
}
