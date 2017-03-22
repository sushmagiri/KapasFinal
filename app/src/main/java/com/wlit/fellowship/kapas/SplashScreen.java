package com.wlit.fellowship.kapas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wlit.fellowship.kapas.helper.SQLiteHandler;
import com.wlit.fellowship.kapas.helper.SessionManager;


public class SplashScreen extends AppCompatActivity {

    public static SharedPreferences app_preferences;
    SharedPreferences.Editor editor;
    public static boolean fbsignedIn;
    public static boolean opsignedIn;
    String useremail, password;
    int loginfromSplash = 1;
    private SQLiteHandler db;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);



        Thread thread = new Thread() {
            public void run() {
                try {

                    sleep(3000);

                } catch (InterruptedException ex) {
                  ex.printStackTrace();
                } finally {
//                    db = new SQLiteHandler(getApplicationContext());
//
//                    // session manager
//                    session = new SessionManager(getApplicationContext());
//
//                    if (session.isLoggedIn()) {
//                        logoutUser();
//                    }
//                        Logger.d("Checking", "i Amhasdjs");
                        Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                        startActivity(intent);
                        finish();


//                        String URL = LinkConfig.getString(SplashScreen.this, LinkConfig.LOGIN_URL)
//                                + "?uname="
//                                + useremail
//                                + "&pswd=" + password;
//
//                        new LoginTask(SplashScreen.this, useremail, password,loginfromSplash).execute(URL);

                }
            }
        };
        thread.start();
    }
}
