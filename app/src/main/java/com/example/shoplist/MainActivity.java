package com.example.shoplist;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button itemlist = findViewById(R.id.button_ListItem);
        Button additem = findViewById(R.id.button_additem);
        Button deleteitem = findViewById(R.id.button_deleteitem);
        Button check = findViewById(R.id.button_check);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        String s1 = sharedPreferences.getString("userid", "");

        itemlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ItemListActivity.class);
                startActivity(intent);
            }
        });
        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddItemActivity.class);
                startActivity(intent);
            }
        });
        deleteitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ItemListActivity.class);
                startActivity(intent);
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,s1,Toast.LENGTH_LONG).show();
            }
        });
    }
}