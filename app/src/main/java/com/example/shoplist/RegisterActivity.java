package com.example.shoplist;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText rlogin = findViewById(R.id.editText_RegisterLogin);
        EditText rpassword = findViewById(R.id.editText_RegisterPassword);
        EditText remail = findViewById(R.id.editText_RegisterEmail);
        Button submit = findViewById(R.id.button_RegisterSubmit);

        AsyncHttpClient client = new AsyncHttpClient();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = rlogin.getText().toString();
                String password = rpassword.getText().toString();
                String email = remail.getText().toString();

                if(login.isEmpty()||password.isEmpty()||email.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle(R.string.Error)
                            .setMessage(R.string.Empty)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    String url="http://dev.imagit.pl/wsg_zaliczenie/api/register";
                    RequestParams params = new RequestParams();
                    params.put("login", login);
                    params.put("pass",password);
                    params.put("email",email);
                    client.post(url, params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String response = new String(responseBody);
                            if (response.equals("OK")){
                                Toast.makeText(RegisterActivity.this,R.string.UserRegisteredSuccessfully,Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegisterActivity.this,LogInActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(RegisterActivity.this,R.string.UsernameAlreadyExists,Toast.LENGTH_LONG).show();
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