package com.bdlabit.shaqib.jubot;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class LoginActivity extends AppCompatActivity {

    TextView loginBtn, signUp, forgotBtn;
    EditText phoneNo, password;
    String number, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.btn_login);
        forgotBtn = findViewById(R.id.tv_forgot_password);
        signUp = findViewById(R.id.tv_signUp_here);
        phoneNo = findViewById(R.id.et_phone);
        password = findViewById(R.id.et_pass);

        Paper.init(this);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = "+88" + phoneNo.getText().toString();
                Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                intent.putExtra("phoneNumber",number);
                startActivity(intent);
                finish();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTermsAndConditions();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = "+88" + phoneNo.getText().toString();
                pass = password.getText().toString();

                if(number.length() != 14){
                    Toast.makeText(LoginActivity.this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
                    phoneNo.requestFocus();
                }
                else if (pass.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Enter your password", Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                }

                else if (Common.isConnectedToInternet(getBaseContext())) {

                    final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage("Logging in...");
                    progressDialog.show();

                    table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if (dataSnapshot.child(number).exists()) {
                                    progressDialog.dismiss();
                                    User user = dataSnapshot.child(number).getValue(User.class);
                                    user.setPhone(number);
                                    Common.currentUser = user;
                                    if (pass.equals(user.getPassword()) ){
                                        Paper.book().write(Common.phn, number);
                                        startActivity(new Intent(LoginActivity.this, TabActivity.class));
                                        finish();
                                    } else{
                                        Toast.makeText(LoginActivity.this, "Password Incorrect", Toast.LENGTH_SHORT).show();
                                        forgotBtn.setVisibility(View.VISIBLE);
                                    }

                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "Please register first.", Toast.LENGTH_SHORT).show();
                                }
                            }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    Toast.makeText(LoginActivity.this, "Please check your internet connection and try again...", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void showTermsAndConditions() {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
            alertDialog.setTitle("TERMS AND CONDITIONS");

            final TextView tAndC = new TextView(LoginActivity.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );

            tAndC.setLayoutParams(layoutParams);
            tAndC.setPadding(50,10,50,50);
            tAndC.setTextSize(18);
            alertDialog.setView(tAndC);
            tAndC.setText("১. সার্ভিস চার্জ সর্বনিম্ন ৫ টাকা। এছাড়া প্রতি ১০০ টাকায় ৫ টাকা সার্ভিস চার্জ প্রযোজ্য।\n" +
                    "২. শুধুমাত্র বটতলায় প্রাপ্ত খাবার সমূহ থেকে ডেলিভারি দেয়া হবে।\n" +
                    "৩. খাবার ডেলিভারির সময় দুপুর ১ টা থেকে ২.৩০ টা এবং রাত ৮.৩০ টা থেকে ১০.০০ টা।\n" +
                    "৪. দুপুর ১.০০ টা থেকে দুপুর ৩ টা পর্যন্ত এবং রাত ৮ টা থেকে থেকে রাত ১১ টা পর্যন্ত কোন অর্ডার করা যাবে না। দুপুর ১.০০ টার পূর্বে অর্ডারকৃত খাবার দুপুরের অর্ডার এবং রাত ৮ টার পূর্বে অর্ডারকৃত খাবার রাতের অর্ডার বলে গন্য হবে।\n" +
                    "৫. খাবার ডেলিভারি করা হবে হল ভিত্তিক এবং ডিপার্টমেন্ট ভিত্তিক (রুম ভিত্তিক নয়)।\n" +
                    "৬. প্রদানকৃত অর্ডার বাতিল করতে হলে অর্ডারের জন্য বরাদ্দকৃত শেষ সময়ের ৩০ মিনিট পূর্বে জানাতে হবে। অন্যথা হলে অর্ডার বাতিল হবে না।\n" +
                    "৭. অর্ডারকৃত খাবার ডেলিভারি দিতে অক্ষম হলে কর্তৃপক্ষ সম্পূর্ণ চার্জ ফেরত প্রদান করবে।");
            alertDialog.setIcon(R.drawable.ic_assignment_black_24dp);

            alertDialog.setPositiveButton("I agree", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(LoginActivity.this,SignupActivity.class));
                    finish();
                }
            });

            alertDialog.show();


        }

}
