package com.example.shoplist;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;

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
        String url = "http://dev.imagit.pl/wsg_zaliczenie/api/login/"+userId;


    }
}