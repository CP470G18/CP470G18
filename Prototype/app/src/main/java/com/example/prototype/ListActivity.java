package com.example.prototype;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "ListActivity";
    ArrayList<String> temp_store;
    ListView the_list;
    ArrayAdapter<String> adapter;
    androidx.appcompat.widget.Toolbar the_tool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        the_list= (ListView) findViewById(R.id.list);
        //the_tool=(androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_list);
        Toolbar toolbar = findViewById(R.id.toolbar_list);
        setSupportActionBar(toolbar);
        temp_store=new ArrayList<String>();
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,temp_store);
        the_list.setAdapter(adapter);
        //the_tool.setOnClickListener(new View.OnClickListener(){

            //@Override
            //public void onClick(View v) {
                //Intent intent = new Intent(ListActivity.this,CreateItemActvity.class);
                //startActivityForResult(intent, 2);
            //}
        //});
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==-1)
        {
            String message=data.getStringExtra("MESSAGE");
            temp_store.add(message);
            adapter.notifyDataSetChanged();

        }
    }

    public boolean onCreateOptionsMenu (Menu m){
        getMenuInflater().inflate(R.menu.menu_lists, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.create_list:
                Log.d("Toolbar","creat_list selected");
                //Intent intent = new Intent(ListActivity.this,CreateItemActvity.class);
                //startActivityForResult(intent, 2);
                //you can do your create list activity here
                return true;
            case R.id.About:
                LayoutInflater inflater = ListActivity.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.custom_dialog,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setView(view);
                builder.setTitle("Help");
                TextView instruction = view.findViewById(R.id.instruction) ;
                instruction.setText("Instrction:\nClick + button to create new group list");
                TextView version = view.findViewById(R.id.version) ;
                String version_name = BuildConfig.VERSION_NAME;
                int version_code = BuildConfig.VERSION_CODE;
                version.setText("versionName:"+version_name+" versionCode:"+version_code);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME,"In onResume()");
    };

    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME,"In onStart()");
    };

    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME,"In onPause()");
    };

    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME,"In onStop()");
    };

    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME,"In onDestroy()");
    };


}