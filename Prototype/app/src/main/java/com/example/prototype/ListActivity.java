package com.example.prototype;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.BuildConfig;

import java.util.ArrayList;
import java.util.Collections;

public class ListActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "ListActivity";
    ArrayList<String> temp_store;
    ArrayList<String> price_store;
    ArrayList<String> desc_store;
    int costTotal;
    ListView the_list;
    TextView the_price_total;
    private ArrayList<String> list_keys;
    ArrayAdapter<String> adapter;
    androidx.appcompat.widget.Toolbar the_tool;
    String listKey;
    String listName;
    private FirebaseDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        the_list= (ListView) findViewById(R.id.list);
        the_price_total= (TextView) findViewById(R.id.priceView);
        //the_tool=(androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_list);
        Toolbar toolbar = findViewById(R.id.toolbar_list);
        Intent intent=getIntent();
        costTotal=0;
        listKey = intent.getStringExtra("Key");
        listName = intent.getStringExtra("Name");
        //Log.i("THis is it", listKey);
        toolbar.setTitle(listName);
        setSupportActionBar(toolbar);
        the_tool=(androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_list);
        list_keys = new ArrayList<>();

        temp_store=new ArrayList<String>();
        desc_store=new ArrayList<String>();
        price_store=new ArrayList<String>();
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,temp_store);
        the_list.setAdapter(adapter);
        dbHelper = new FirebaseDatabaseHelper();

        the_list.setClickable(true);
        the_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle args = new Bundle();
                args.putString("message",temp_store.get(position));
                args.putLong("the_id",position);
                args.putString("Price",price_store.get(position));
                args.putString("Desc",desc_store.get(position));
                args.putString("listKey",listKey);
                args.putString("key", list_keys.get(position));
                args.putString("phone","yes");
                Intent intent = new Intent(ListActivity.this,ItemDetails.class);
                intent.putExtra("bundle",args);
                startActivityForResult(intent,2);
                adapter.notifyDataSetChanged();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(resultCode==0){

        }
        else if(resultCode==-1)
        {
            String message=data.getStringExtra("Response");
            String price=data.getStringExtra("Price");
            String desc =data.getStringExtra("Desc");
            Log.i("HELPME",price);

            price_store.add(price);
            //costTotal+=Integer.parseInt(price);
            desc_store.add(desc);
            Log.i("The test",message);
            temp_store.add(message);
            adapter.notifyDataSetChanged();

        }
        else if(resultCode!=0){
            Log.i("ERROR", String.valueOf(resultCode));
            temp_store.remove(resultCode-1);
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
                Intent intent = new Intent(ListActivity.this,CreateItemActivity.class);
                intent.putExtra("listKey", listKey);
                startActivityForResult(intent, 2);
                adapter.notifyDataSetChanged();
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
            case R.id.ascending:
                sortA();
                adapter.notifyDataSetChanged();
                return true;
            case R.id.descending:
                sortB();
                adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    protected void onResume(){
        super.onResume();
        super.onResume();
        Log.i(ACTIVITY_NAME,"In onResume()");
        populate();
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

    private void populate() {
        costTotal=0;
        dbHelper.readItems(listKey, new FirebaseDatabaseHelper.ItemDataStatus() {


            @Override
            public void DataIsLoaded(ArrayList<Item> items, ArrayList<String> keys) {
                temp_store.clear();
                Log.i("Warning","Populating table");
                desc_store.clear();
                price_store.clear();
                list_keys.clear();
                for (int i = 0; i<items.size(); i++) {
                    Log.i("Testing",String.valueOf(costTotal));

                        temp_store.add(items.get(i).getName());
                        price_store.add(String.valueOf(items.get(i).getCost()));
                        costTotal+=items.get(i).getCost();
                        the_price_total.setText(String.valueOf(costTotal));
                        desc_store.add(items.get(i).getDescription());
                        list_keys.add(keys.get(i));
                        adapter.notifyDataSetChanged();


                }
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }

    private void sortA() {
        for (int i = 1; i < price_store.size(); i++) {
            String currentPrice = price_store.get(i);
            String currentTemp = temp_store.get(i);
            String currentDesc = desc_store.get(i);
            String currentKey = list_keys.get(i);
            int j = i - 1;
            while (j >= 0 && Integer.parseInt(currentPrice) < Integer.parseInt(price_store.get(j))) {
                price_store.set(j+1, price_store.get(j));
                temp_store.set(j+1, temp_store.get(j));
                desc_store.set(j+1, desc_store.get(j));
                list_keys.set(j+1, list_keys.get(j));
                j--;
            }
            price_store.set(j+1, currentPrice);
            temp_store.set(j+1, currentTemp);
            desc_store.set(j+1, currentDesc);
            list_keys.set(j+1, currentKey);
        }
    }

    private void sortB() {
        sortA();
        Collections.reverse(price_store);
        Collections.reverse(temp_store);
        Collections.reverse(desc_store);
        Collections.reverse(list_keys);
    }

}
