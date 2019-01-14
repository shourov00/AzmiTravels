package com.projectidea.shourov.azmitravels;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GetStartedActivity extends AppCompatActivity {

    @BindView(R.id.image_splash_bg)
    ImageView imageSplashBg;
    @BindView(R.id.text_splash)
    TextView textSplash;
    @BindView(R.id.btn_splash)
    Button btnSplash;

    private FirebaseAuth mAuth;

    private Animation mFromBottomTop, mSmallToBig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        ButterKnife.bind(this);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        mFromBottomTop = AnimationUtils.loadAnimation(this, R.anim.from_top_bottom);
        mSmallToBig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);

        textSplash.startAnimation(mFromBottomTop);
        btnSplash.startAnimation(mFromBottomTop);
        imageSplashBg.startAnimation(mSmallToBig);

        btnSplash.setOnClickListener(v -> {
            //moving signin activity
            GetStartedActivity.this.startActivity(new Intent(GetStartedActivity.this, SignUpActivity.class));
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI();
        }
    }

    private void updateUI() {
        startActivity(new Intent(GetStartedActivity.this, MainActivity.class));
        finish();
    }
}
