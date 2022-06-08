package com.example.shoplist;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView_Products = findViewById(R.id.listView_Products);
        SharedPreferences prefernces = getSharedPreferences("userPrefernces", Activity.MODE_PRIVATE);
        String userId = prefernces.getString("userId","0");
        ArrayList<String> ProductList = new ArrayList<String>();
        ArrayAdapter<String> PoductAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,ProductList);
        listView_Products.setAdapter(PoductAdapter);

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://dev.imagit.pl/wsg_zaliczenie/api/items/"+userId;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                try {
                    JSONArray jArray = new JSONArray(json);
                    for(int i =0; i<jArray.length(); i++){
                        JSONObject jObject = jArray.getJSONObject(i);
                        String productName = jObject.getString("ITEM_NAME");
                        String itemDescription =jObject.getString("ITEM_DESCRIPTION");

                        ProductList.add(productName + ", " + itemDescription);
                    }
                    listView_Products.setAdapter(PoductAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });


    }
}