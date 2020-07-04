package com.example.android.aegis_proto5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    TextView textViewWelcome;
    FirebaseAuth firebaseAuth;
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textViewWelcome = (TextView)findViewById(R.id.textViewWelcome);
        textViewWelcome.setText("WELCOME");

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onBackPressed(){
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            backToast.cancel();
            firebaseAuth.signOut();
            return;
        } else {
            backToast = Toast.makeText(HomeActivity.this, "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}
