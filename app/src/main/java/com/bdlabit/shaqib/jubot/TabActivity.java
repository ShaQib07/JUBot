package com.bdlabit.shaqib.jubot;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andremion.counterfab.CounterFab;
import com.bdlabit.shaqib.jubot.Common.Common;
import com.bdlabit.shaqib.jubot.Database.Database;
import com.bdlabit.shaqib.jubot.Model.User;
import com.bdlabit.shaqib.jubot.viewHolder.ViewPagerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Objects;

import io.paperdb.Paper;

public class TabActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TabLayout tabLayout;
    AppBarLayout appBarLayout;
    ViewPager viewPager;
    TextView userName, userNo;
    CounterFab fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        tabLayout = findViewById(R.id.tabs);
        appBarLayout = findViewById(R.id.appbar);
        viewPager = findViewById(R.id.viewpager);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TabActivity.this,CartActivity.class));
            }
        });

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.AddFragment(new RiceFragment(), "ভাত");
        pagerAdapter.AddFragment(new VegFragment(), "তরকারি");
        pagerAdapter.AddFragment(new VortaFragment(), "ভর্তা");
        pagerAdapter.AddFragment(new OthersFragment(), "অন্যান্য");
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        //recreating home activity

        Paper.init(this);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference version = database.getReference("Version");

        version.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String currentVersion = getResources().getString(R.string.version_code);
                if (!dataSnapshot.child(currentVersion).exists()){
                    showUpdateDialog();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {

                if (task.isSuccessful()){

                    String token = Objects.requireNonNull(task.getResult()).getToken();
                    saveToken(token);
                }
            }
        });
        //getting FCM token

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TabActivity.this,CartActivity.class));
            }
        });

        fab.setCount(new Database(this).getCountCart());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        userName = headerView.findViewById(R.id.header_name);
        userName.setText(Common.currentUser.getName());
        userNo = headerView.findViewById(R.id.header_phone);
        userNo.setText(Common.currentUser.getPhone());

    }

    private void showUpdateDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(TabActivity.this)
                .setTitle("UPDATE AVAILABLE")
                .setMessage("A new version is available on play store.\nPlease update.")
                .setCancelable(false)
                .setPositiveButton("Update now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String appPackageName = getPackageName();
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe){
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+appPackageName)));
                        }
                        finish();
                    }
                });

        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            showQuitDialog();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_menu) {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(TabActivity.this,ProfileActivity.class));
        } else if (id == R.id.nav_schedule){
            showDeliverySchedule();
        }else if (id == R.id.nav_order) {
            startActivity(new Intent(TabActivity.this,MyOrdersActivity.class));
        }else if (id == R.id.nav_payment) {
            startActivity(new Intent(TabActivity.this,PaymentActivity.class));
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(TabActivity.this,AboutusActivity.class));
        } else if (id == R.id.nav_logout) {
            showAlertDialog();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        fab.setCount(new Database(this).getCountCart());
        userName.setText(Common.currentUser.getName());
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(TabActivity.this);
        alertDialog.setTitle("LOG OUT");
        alertDialog.setMessage("Do you wish to log out?\nAny item in your cart will be removed!");

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Paper.book().destroy();
                Intent user = new Intent(TabActivity.this,LoginActivity.class);
                user.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(user);

                new Database(getBaseContext()).cleanCart();
                finish();
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();


    }
    private void showQuitDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(TabActivity.this);
        alertDialog.setTitle("QUIT");
        alertDialog.setMessage("Are you sure you wish to quit?\nAny item in your cart will be removed!");

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Database(getBaseContext()).cleanCart();
                finish();
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();


    }

    private void showDeliverySchedule() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(TabActivity.this);
        alertDialog.setTitle("DELIVERY SCHEDULE");

        final TextView tAndC = new TextView(TabActivity.this);
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

    private void saveToken(String token) {

        User user = new User(Common.currentUser.getPhone(),
                Common.currentUser.getPassword(),
                Common.currentUser.getName(),
                Common.currentUser.getDept(),
                Common.currentUser.getHall(),
                Common.currentUser.getRoom(),
                Common.currentUser.getBatch(),
                Common.currentUser.getEmail(),
                token);
        Common.currentUser = user;

        DatabaseReference dbUser = FirebaseDatabase.getInstance().getReference("User");

        dbUser.child(Common.currentUser.getPhone()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(TabActivity.this, "Token saved", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
