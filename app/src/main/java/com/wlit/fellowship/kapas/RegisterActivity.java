package com.wlit.fellowship.kapas;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {
    Button btn_linktologin, btn_reg;
    RequestQueue requestQueue;
    EditText et_name, et_email, et_password;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String REGISTER_URL = "http://kapas.wlit.org.np/register";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_name = (EditText) findViewById(R.id.reg_name);
        et_email = (EditText) findViewById(R.id.reg_email);
        et_password = (EditText) findViewById(R.id.reg__password);
        btn_reg = (Button) findViewById(R.id.btnRegister);
        btn_linktologin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        btn_linktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        sharedPreferences = getApplicationContext().getSharedPreferences("Reg", 0);
// get editor to edit in file
        editor = sharedPreferences.edit();
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });


    }

    private void registerUser() {
        final String username = et_name.getText().toString().trim();
        final String email = et_email.getText().toString().trim();
        final String password = et_password.getText().toString().trim();
        if (et_name.getText().length() <= 0) {
            Toast.makeText(RegisterActivity.this, "Enter name", Toast.LENGTH_SHORT).show();
        } else if (et_email.getText().length() <= 0) {
            Toast.makeText(RegisterActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
        } else if (et_password.getText().length() <= 0) {
            Toast.makeText(RegisterActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
        } else {


            Map<String, String> params = new HashMap<>();
            params.put("username", username);
            params.put("email", email);
            params.put("password", password);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, REGISTER_URL,
                    new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            editor.putString("Name", username);
                            editor.putString("Email", email);
                            editor.putString("Password", password);
                            editor.commit();  // commit the values
                            finish();
                            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
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

        }
    }
    }
