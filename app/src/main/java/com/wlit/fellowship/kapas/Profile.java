package com.wlit.fellowship.kapas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class Profile extends AppCompatActivity {
    // Alert Dialog Manager


    // Session Manager Class
    UserSession session;

    // Button Logout
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // Session class instance
        session= new UserSession(getApplicationContext());

//        TextView lblName = (TextView) findViewById(R.id.lblName);
//        TextView lblEmail = (TextView) findViewById(R.id.lblEmail);

        // Button logout
        btnLogout = (Button) findViewById(R.id.btnLogout);

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isUserLoggedIn(), Toast.LENGTH_LONG).show();


        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

//        // name
//        String name = user.get(SessionManagement.KEY_NAME);
//
//        // email
//        String email = user.get(SessionManagement.KEY_EMAIL);

//        // displaying user data
//        lblName.setText(Html.fromHtml("Name: <b>" + name + "</b>"));
//        lblEmail.setText(Html.fromHtml("Email: <b>" + email + "</b>"));


        /**
         * Logout button click event
         * */
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Clear the session data
                // This will clear all session data and
                // redirect user to LoginActivity
                session.logoutUser();
            }
        });
    }
    }

