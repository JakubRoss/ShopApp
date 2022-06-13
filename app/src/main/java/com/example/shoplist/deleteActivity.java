package com.example.shoplist;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class deleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        ListView listView_Products = findViewById(R.id.listView_Products);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        String s1 = sharedPreferences.getString("userid", "");

        ArrayList<String> ProductList = new ArrayList<String>();
        ArrayAdapter<String> PoductAdapter = new ArrayAdapter<String>(deleteActivity.this, android.R.layout.simple_list_item_1,ProductList);
        listView_Products.setAdapter(PoductAdapter);

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://dev.imagit.pl/wsg_zaliczenie/api/items/"+s1;

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
                        String itemid =jObject.getString("ITEM_ID");

                        ProductList.add(productName + "     " + itemDescription + "     "+itemid);

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

        listView_Products.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               String element = ProductList.get(position);
                String[] namesArray = null;
                namesArray = element.split("     ");
                String productitemname =namesArray[0];
                String productitemid = namesArray[2];
                String url2 ="http://dev.imagit.pl/wsg_zaliczenie/api/item/delete/"+s1+"/"+productitemid;
                String todelete = "Do you want to remove " + productitemname +"?";
                AlertDialog.Builder builder = new AlertDialog.Builder(deleteActivity.this);
                builder.setTitle(R.string.sure)
                        .setMessage(todelete)
                        .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                client.get(url2, new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                        String odp = new String(responseBody);
                                        String odp2 = "OK";
                                        if(odp2.equals(odp)){
                                            Toast.makeText(deleteActivity.this, R.string.ItemDeletedSuccessfully, Toast.LENGTH_LONG).show();
                                        }
                                        else
                                            Toast.makeText(deleteActivity.this, R.string.Error, Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                    }
                                });
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();


                return false;
            }
        });

    }}