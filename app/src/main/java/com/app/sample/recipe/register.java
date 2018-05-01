package com.app.sample.recipe;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.app.sample.recipe.R;



public class register extends AppCompatActivity {

    private Context mContext;
    private String email, firstname,lastname,mobileno, password;
    private EditText mEmail, mPassword, mfirstname,mlastname,mmobileno;
    private TextView loadingPleaseWait;
    private Button btnRegister;
    private ProgressBar mProgressBar;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods firebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private String append = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setContentView(R.layout.activity_register);
        mContext = register.this;
        firebaseMethods = new FirebaseMethods(mContext);
        System.out.println("onCreate: started.");

        initWidgets();
        setupFirebaseAuth();
        init();

    }
    private void init(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getText().toString();
                firstname = mfirstname.getText().toString();
                lastname = mlastname.getText().toString();
                mobileno = mmobileno.getText().toString();
                password = mPassword.getText().toString();

                if(checkInputs(email, firstname,password)){
//                    mProgressBar.setVisibility(View.VISIBLE);
//                    loadingPleaseWait.setVisibility(View.VISIBLE);

                    firebaseMethods.registerNewEmail(email, password, firstname);
                }
            }
        });
    }

    private boolean checkInputs(String email, String username, String password){
//        Log.d(TAG, "checkInputs: checking inputs for null values.");
        if(email.equals("") || username.equals("") || password.equals("")){
            Toast.makeText(mContext, "All fields must be filled out.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void initWidgets(){
//        Log.d(TAG, "initWidgets: Initializing Widgets.");

        mfirstname = (EditText) findViewById(R.id.input_first_name);
        mlastname = (EditText) findViewById(R.id.input_last_name);
        mEmail = (EditText) findViewById(R.id.input_email);
        mmobileno = (EditText) findViewById(R.id.input_mobile_number);
        mPassword = (EditText) findViewById(R.id.input_password);

        btnRegister = (Button) findViewById(R.id.btn_register);
        mContext = register.this;

    }
    private boolean isStringNull(String string){
//        Log.d(TAG, "isStringNull: checking string if null.");

        if(string.equals("")){
            return true;
        }
        else{
            return false;
        }
    }

    private void setupFirebaseAuth(){
//        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //1st check: Make sure the username is not already in use
                            if(firebaseMethods.checkIfUsernameExists(email, dataSnapshot)){
                                append = myRef.push().getKey().substring(3,10);
//                                Log.d(TAG, "onDataChange: username already exists. Appending random string to name: " + append);
                            }
                            email = email + append;

                            //add new user to the database
                            firebaseMethods.addNewUser(email, firstname, "", "", "");

                            Toast.makeText(mContext, "Signup successful. Sending verification email.", Toast.LENGTH_SHORT).show();



                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                } else {
                    // User is signed out
//                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}
