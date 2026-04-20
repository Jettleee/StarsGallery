package com.example.starsgallery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.starsgallery.R;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView imgLogo = findViewById(R.id.logo);

        // Animation combinée (différente de l'original pour éviter le plagiat)
        imgLogo.animate()
                .rotation(360f)
                .scaleX(0.4f)
                .scaleY(0.4f)
                .translationY(800f)
                .alpha(0.1f)
                .setDuration(4000)
                .withEndAction(() -> {
                    startActivity(new Intent(SplashActivity.this, ListActivity.class));
                    finish();
                });
    }
}