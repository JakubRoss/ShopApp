package com.example.shoplist;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class LogInActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        EditText username = findViewById(R.id.editText_EmailAddress);
        EditText password = findViewById(R.id.editText_Password);
        TextView register = findViewById(R.id.textView_register);
        Button login = findViewById(R.id.button_logIn);
        AsyncHttpClient client = new AsyncHttpClient();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = username.getText().toString();
                String name = password.getText().toString();
                if(login.isEmpty() || name.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LogInActivity.this);
                    builder.setTitle(R.string.Error)
                            .setMessage(R.string.Empty)
                            .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    String url = "http://dev.imagit.pl/wsg_zaliczenie/api/login/"+login+"/"+name;
                    client.get(url, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String response = new String(responseBody);
                            if(android.text.TextUtils.isDigitsOnly(response)){


                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                myEdit.putString("userid",response);
                                myEdit.apply();

                                Intent intent = new Intent(LogInActivity.this,MainActivity.class);
                                startActivity(intent);

                            }
                            else {
                                Toast.makeText(LogInActivity.this, R.string.Invalid, Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });

                }

            }
        });
    }
}