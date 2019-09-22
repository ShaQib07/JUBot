package com.bdlabit.shaqib.jubot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bdlabit.shaqib.jubot.Common.Common;
import com.bdlabit.shaqib.jubot.Model.Review;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReviewActivity extends AppCompatActivity {

    EditText ans1, ans2, ans3, ans4, ans5;
    TextView submitBtn, cancelBtn;
    String name, phone,  ansOne, ansTWo, ansThree, ansFour, ansFive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reviews = database.getReference("Review");


        ans1 = findViewById(R.id.et_ans1);
        ans2 = findViewById(R.id.et_ans2);
        ans3 = findViewById(R.id.et_ans3);
        ans4 = findViewById(R.id.et_ans4);
        ans5 = findViewById(R.id.et_ans5);

        submitBtn = findViewById(R.id.btn_submit);
        cancelBtn = findViewById(R.id.btn_cancel);

        if (Common.currentUser == null){
            Toast.makeText(this, "Oops! you need to login first.", Toast.LENGTH_LONG).show();
            finish();
        }

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

                name = Common.currentUser.getName();
                phone = Common.currentUser.getPhone();
                ansOne = ans1.getText().toString();
                ansTWo = ans2.getText().toString();
                ansThree = ans3.getText().toString();
                ansFour = ans4.getText().toString();
                ansFive = ans5.getText().toString();

                    Review review = new Review(name, phone, ansOne, ansTWo, ansThree, ansFour, ansFive);
                    reviews.child(phone).setValue(review);
                    Toast.makeText(ReviewActivity.this, "||Feedback submitted||\nThanks for reviewing.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ReviewActivity.this, TabActivity.class));
                    finish();
            }
        });

    }
}
