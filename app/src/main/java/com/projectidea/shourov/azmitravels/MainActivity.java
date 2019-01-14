package com.projectidea.shourov.azmitravels;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ImageListAdapter.OnItemClickListener {
    private static final String TAG = "MainActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progress_circular)
    ProgressBar progressCircular;

    private ImageView mHeaderImage;
    private TextView mHeaderUsername;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private ImageListAdapter mAdapter;
    private DatabaseReference mDatabaseReference;
    private List<Upload> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setToolbar();
        navigationSetUpWithCustomHeader();
        firebaseAuthSetup();
        setUpRecyclerView();
        navigationItemClicked();
        retriveDataFromFirebaseDatabaseRef();
        setNotificationChannel();
        setNotificationTopic();

        //check internet connection
        if (!haveAnyNetwork()) {
            progressCircular.setVisibility(View.GONE);
            Snackbar.make(drawerLayout, "No Internet Connection", Snackbar.LENGTH_LONG).show();
        }
    }

    private void setNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel("MyNotification", "MyNotification", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void setNotificationTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(task -> {
                    String msg = "Successful";
                    if (!task.isSuccessful()) {
                        msg = "Unsuccessful";
                    }
                    Log.d(TAG, "onComplete: " + msg);
                });
    }

    private void retriveDataFromFirebaseDatabaseRef() {
        //get data from firebase database and add them to sUpload class
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShort : dataSnapshot.getChildren()) {
                    Upload upload = postSnapShort.getValue(Upload.class);
                    mUploads.add(upload);
                }

                mAdapter = new ImageListAdapter(MainActivity.this, mUploads);
                recyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(MainActivity.this);
                progressCircular.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Failed to show image", Toast.LENGTH_SHORT).show();
                progressCircular.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onItemCLick(int position) {
        //send values from firebase database
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("positionName", mUploads.get(position).getName());
        intent.putExtra("positionImage", mUploads.get(position).getImageUrl());
        intent.putExtra("positionPersonalPackage", mUploads.get(position).getPersonalPackage());
        intent.putExtra("positionPersonalPrice", mUploads.get(position).getPersonalPrice());
        intent.putExtra("positionGroupPackage", mUploads.get(position).getGroupPackage());
        intent.putExtra("positionGroupPrice", mUploads.get(position).getGroupPrice());
        intent.putExtra("positionVisa", mUploads.get(position).getVisaPackage());
        intent.putExtra("positionVisaPrice", mUploads.get(position).getVisaPrice());
        startActivity(intent);
    }

    private void setUpRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mUploads = new ArrayList<>();

    }

    private void firebaseAuthSetup() {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (user != null) {
            Glide.with(this).load(user.getPhotoUrl()).into(mHeaderImage);
            mHeaderUsername.setText(user.getDisplayName());
        }
    }

    private void navigationSetUpWithCustomHeader() {

        //get header image and text view via navigatiton view
        View headerView = navigationView.getHeaderView(0);
        mHeaderImage = headerView.findViewById(R.id.circle_image);
        mHeaderUsername = headerView.findViewById(R.id.user_name_text);

        //first item initially selected
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    private void navigationItemClicked() {
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.home:
                    drawerLayout.closeDrawer(Gravity.START);
                    break;

                case R.id.booking:
                    //start any bookings activity
                    break;

                case R.id.info:
                    startActivity(new Intent(MainActivity.this, InfoActivity.class));
                    break;

                case R.id.settings:
                    startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                    break;

                case R.id.logout:
                    mAuth.signOut();
                    updateUI();
                    break;
            }

            return false;
        });
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();//toolbar+drawer sync
    }


    @Override
    protected void onStart() {
        super.onStart();
        user = mAuth.getCurrentUser();
        if (user == null) {
            updateUI();
        }
    }

    private void updateUI() {
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
        finish();
    }

    //check network status android
    private boolean haveAnyNetwork() {
        boolean isWifiOn = false;
        boolean isMobileDataOn = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = new NetworkInfo[0];
        if (connectivityManager != null) {
            networkInfos = connectivityManager.getAllNetworkInfo();
        }

        for (NetworkInfo info :
                networkInfos) {
            if (info.getTypeName().equalsIgnoreCase("WIFI"))
                if (info.isConnected()) isWifiOn = true;
            if (info.getTypeName().equalsIgnoreCase("MOBILE"))
                if (info.isConnected()) isMobileDataOn = true;
        }

        return isMobileDataOn || isWifiOn;
    }
}
