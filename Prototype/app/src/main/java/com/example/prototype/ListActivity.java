package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    ArrayList<String> temp_store;
    ListView the_list;
    ArrayAdapter<String> adapter;
    Toolbar the_tool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        the_list= (ListView) findViewById(R.id.list);
        the_tool=(Toolbar) findViewById(R.id.toolbar_list);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,temp_store);
        the_list.setAdapter(adapter);
        the_tool.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this,CreateItemActvity.class);
                startActivityForResult(intent, 2);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            String message=data.getStringExtra("MESSAGE");
            temp_store.add(message);
            adapter.notifyDataSetChanged();

        }
    }


}