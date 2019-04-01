package com.bdlabit.shaqib.jubot;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AboutusActivity extends AppCompatActivity {

    ImageButton backBtn;
    TextView share, tAndC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        share = findViewById(R.id.share);
        tAndC = findViewById(R.id.tAndC);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(Intent.ACTION_SEND);
                final String appPackageName = getApplicationContext().getPackageName();
                String appLink="https://play.google.com/store/apps/details?id=" +appPackageName;

                intent.setType("text/plain");
                String shareBody = "Hey!" + "\n"+""+appLink;
                String shareSub = "APP NAME/TITLE";
                intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent, "Share Using"));
            }
        });

        tAndC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTermsAndConditions();
            }
        });
    }

    private void showTermsAndConditions() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AboutusActivity.this);
        alertDialog.setTitle("TERMS AND CONDITIONS");

        final TextView tAndC = new TextView(AboutusActivity.this);
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


        alertDialog.show();


    }
}
