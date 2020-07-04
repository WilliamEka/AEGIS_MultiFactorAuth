package com.example.android.aegis_proto5;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.aegis_proto5.Model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference users;

    EditText editEmail, editUsername, editPassword, editPhone;
    Button btnSignUp;
    TextView textViewSignIn, textIMEI;

    String imei_numHold;
    TelephonyManager telephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        users = database.getReference();

        editEmail = (EditText) findViewById(R.id.editEmail);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);
        textIMEI = (TextView)findViewById(R.id.textIMEI);

        telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        textViewSignIn = (TextView) findViewById(R.id.textViewSignIn);
        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(s);
            }
        });

        btnSignUp = (Button) findViewById(R.id.btnRegister);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;//
                }

                imei_numHold = telephonyManager.getDeviceId();
                textIMEI.setText(imei_numHold);

                final User user = new User(
                        editEmail.getText().toString(),
                        editUsername.getText().toString(),
                        editPassword.getText().toString(),
                        editPhone.getText().toString(),
                        textIMEI.getText().toString());

                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(user.getEmail().isEmpty()){
                            editEmail.setError("Please enter your email address");
                            editEmail.requestFocus();
                            }else if(user.getPhone().isEmpty() || user.getPhone().length() < 10){
                                editPhone.setError("Please enter valid phone number");
                                editPhone.requestFocus();
                            }else if(user.getUsername().isEmpty() || user.getUsername().length() < 5){
                                editUsername.setError("Please enter username (min. 5 characters");
                                editUsername.requestFocus();
                            }else if(user.getPassword().isEmpty()){
                                editPassword.setError("Please enter your password");
                                editPassword.requestFocus();
                            }else if (dataSnapshot.child(user.getImei()).exists()) {
                                Toast.makeText(MainActivity.this, "Phone's already registered", Toast.LENGTH_SHORT).show();
                        }else{
                            users.child(user.getImei()).setValue(user);
                            Toast.makeText(MainActivity.this, "Successfully registered !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}

