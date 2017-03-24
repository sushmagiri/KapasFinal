package com.wlit.fellowship.kapas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.client.HttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.makeText;


public class LoginActivity extends AppCompatActivity {
    String response;
    EditText et_username, et_pass;
    ProgressDialog progressDialog;
    HttpClient client;
    String id;

    private static final String PREFER_NAME = "Reg";

    // User Session Manager Class
    UserSession session;

    private SharedPreferences sharedPreferences;


    private static final String LOGIN_URL = "http://kapas.wlit.org.np/login";


    Button btnRegisterScreen,login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session= new UserSession(getApplicationContext());
        et_username = (EditText) findViewById(R.id.login_name);
        et_pass = (EditText) findViewById(R.id.login_password);
        makeText(getApplicationContext(), "User Login Status: " + session.isUserLoggedIn(), Toast.LENGTH_LONG).show();
        sharedPreferences = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        btnRegisterScreen=(Button)findViewById(R.id.btnLinkToRegisterScreen);
        login=(Button)findViewById(R.id.btnLoginSc);
        btnRegisterScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });



    }

    public void userLogin() {

        final String username = et_username.getText().toString().trim();
        final String password = et_pass.getText().toString().trim();
        if (username.trim().length() > 0 && password.trim().length() > 0) {
            Map<String, String> params = new HashMap<>();

            params.put("username", username);
            params.put("password", password);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, LOGIN_URL,
                    new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
              //TODO for passs id and email
                            String responseUsername = null;
                            String responsePassword=null;
                            try {
                                responseUsername = response.getString("username");
                                responsePassword=response.getString("password");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            session.createUserLoginSession(responseUsername, responsePassword);
                            Log.d("values", session.toString());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("username", username);
                            startActivity(intent);
                            finish();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    }
            );


            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);
        } else {

            // user didn't entered username or password

        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
