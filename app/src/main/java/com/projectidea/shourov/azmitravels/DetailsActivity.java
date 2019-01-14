package com.projectidea.shourov.azmitravels;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {
    private static final int REQUEST_CALL = 821;
    private static final String KEY_PHONE_NUMBER = "+880161651111";
    private static final String KEY_PERMISSION_CALL = Manifest.permission.CALL_PHONE;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.packageImage)
    ImageView packageImage;

    @BindView(R.id.personalTourPackage)
    TextView personalTourPackage;

    @BindView(R.id.personalTourPrice)
    TextView personalTourPrice;

    @BindView(R.id.callBtn)
    Button callBtn;

    @BindView(R.id.groupTour)
    TextView groupTour;

    @BindView(R.id.visaPackage)
    TextView visaPackage;

    @BindView(R.id.visaPrice)
    TextView visaPrice;

    @BindView(R.id.groupTourPrice)
    TextView groupTourPrice;

    @BindView(R.id.main_content)
    ConstraintLayout mainContent;

    private Animation mFromBottomTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        mFromBottomTop = AnimationUtils.loadAnimation(this, R.anim.from_top_bottom);

        callBtn.startAnimation(mFromBottomTop);

        //get all values
        Intent intent = getIntent();
        String title = intent.getStringExtra("positionName");
        String image = intent.getStringExtra("positionImage");
        String personalPackage = intent.getStringExtra("positionPersonalPackage");
        String personalPrice = intent.getStringExtra("positionPersonalPrice");
        String groupPackage = intent.getStringExtra("positionGroupPackage");
        String groupPrice = intent.getStringExtra("positionGroupPrice");
        String visa = intent.getStringExtra("positionVisa");
        String visaTourPrice = intent.getStringExtra("positionVisaPrice");

        //set toolbar title
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set images
        Glide.with(this)
                .load(image)
                .into(packageImage);

        //set all text
        personalTourPackage.setText(personalPackage);
        personalTourPrice.setText(personalPrice);
        groupTour.setText(groupPackage);
        groupTourPrice.setText(groupPrice);
        visaPackage.setText(visa);
        visaPrice.setText(visaTourPrice);

        //call button
        callBtn.setOnClickListener(v -> makePhonecall());
    }

    private void makePhonecall() {
        //permission
        if (ContextCompat.checkSelfPermission(DetailsActivity.this, KEY_PERMISSION_CALL) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DetailsActivity.this,
                    new String[]{KEY_PERMISSION_CALL}, REQUEST_CALL);
        } else {
            //if permission granted call then
            String dial = "tel:" + KEY_PHONE_NUMBER;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhonecall();
            } else {
                Snackbar.make(mainContent,"Permission DENIED",Snackbar.LENGTH_LONG).show();
            }
        }
    }
}
