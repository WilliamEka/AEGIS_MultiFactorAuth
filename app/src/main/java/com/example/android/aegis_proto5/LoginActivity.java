package com.example.android.aegis_proto5;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.aegis_proto5.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    FirebaseDatabase database;
    DatabaseReference users;
    DataSnapshot dataSnapshot;

    EditText editUsername, editPassword;
    Button btnSignIn;
    TextView textViewSignUp, textIMEI;

    String imei_numHold;
    TelephonyManager telephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = FirebaseDatabase.getInstance();
        users = database.getReference();

        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);
        textIMEI = (TextView) findViewById(R.id.textIMEI);

        telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        textViewSignUp = (TextView) findViewById(R.id.textViewSignIn);
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(s);
            }
        });

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Log.d(TAG, "onClick: btnSignIn");
                imei_numHold = telephonyManager.getDeviceId();
                signIn(imei_numHold,
                        editUsername.getText().toString(),
                        editPassword.getText().toString());
                Log.d(TAG, "onClick: imei: " + imei_numHold);

            }
        });
    }

    private void signIn(final String imei, final String username, final String password) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: " + dataSnapshot.toString());
                Log.d(TAG, "onDataChange: " + dataSnapshot.getValue().toString());

                if (dataSnapshot.child(imei).exists()) {
                    Log.d(TAG, "onDataChange: " + dataSnapshot.child(imei).getValue().toString());

                    Log.d(TAG, "onDataChange: " + dataSnapshot.child(imei).getValue(User.class).getPassword());
                    Log.d(TAG, "onDataChange: " + dataSnapshot.child(imei).getValue(User.class).getPhone());
                    Log.d(TAG, "onDataChange: " + dataSnapshot.child(imei).getValue(User.class).getEmail());
                    Log.d(TAG, "onDataChange: " + dataSnapshot.child(imei).getValue(User.class).getUsername());

                    if(username.isEmpty()){
                        editUsername.setError("Please enter your username");
                        editUsername.requestFocus();
                    }else if(dataSnapshot.child(imei).getValue(User.class).getUsername().equals(username)) {
                        if(password.isEmpty()){
                            editPassword.setError("Please enter your password");
                            editPassword.requestFocus();
                        }else if (dataSnapshot.child(imei).getValue(User.class).getPassword().equals(password)) {
                            startActivity(new Intent(LoginActivity.this, VerifyPhoneActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Wrong username", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "onDataChange: not exists");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
