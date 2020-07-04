package com.example.android.aegis_proto5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import static java.lang.Thread.sleep;

public class splash_activity extends AppCompatActivity {

    private TextView text;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        text = (TextView)findViewById(R.id.aegis_text);
        image = (ImageView)findViewById(R.id.aegis_logo);
        Animation myAnimation = AnimationUtils.loadAnimation(this, R.anim.transition);

        text.startAnimation(myAnimation);
        image.startAnimation(myAnimation);

        final Intent i = new Intent(this,MainActivity.class);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }
}
