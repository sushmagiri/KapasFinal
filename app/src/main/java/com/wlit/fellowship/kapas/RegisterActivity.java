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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
        if(username.length() == 0){
            et_name.setError("Required");
        } if(email.length() == 0){
            et_email.setError("Required");}
        else if(password.length() == 0){
            et_password.setError("Required");
        }else if(!validEmailAddress(email)) {
            et_email.setError("Invalid Email");
        }else{


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
                            ToastUtils.showToast(RegisterActivity.this, "Unable to RegisterActivity", false);
                        }
                    }
            );


            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);

        }
    }
    public boolean validEmailAddress(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    }
