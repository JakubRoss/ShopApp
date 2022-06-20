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
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class AddItemActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        String s1 = sharedPreferences.getString("userid", "");
        EditText name = findViewById(R.id.editText_AddProductName);
        EditText dsc = findViewById(R.id.editText_AddDescription);
        Button submit = findViewById(R.id.button_AddSubmit);

        AsyncHttpClient client = new AsyncHttpClient();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productname = name.getText().toString();
                String description = dsc.getText().toString();

                if( productname.isEmpty()||description.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddItemActivity.this);
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
                    String url="http://dev.imagit.pl/wsg_zaliczenie/api/item/add";
                    RequestParams params = new RequestParams();
                    params.put("user", s1);
                    params.put("name",productname);
                    params.put("desc",description);
                    client.post(url, params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String response = new String(responseBody);
                            if (response.equals("OK")){
                                Toast.makeText(AddItemActivity.this,R.string.ItemAddedSuccessfully,Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(AddItemActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(AddItemActivity.this,R.string.Error,Toast.LENGTH_LONG).show();
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