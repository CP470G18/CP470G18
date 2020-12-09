package com.example.prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.firebase.BuildConfig;

import java.util.ArrayList;

public class ListsActivity extends AppCompatActivity {

    private ListView lists;
    private ListView price_lists;

    private ArrayList<String> list_names;
    private ArrayList<String> list_prices;
    private ArrayList<String> list_keys;
    private ChatAdapter adapter;
    private ArrayAdapter<String> adp;

    private FirebaseDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        lists = (ListView) findViewById(R.id.lists);
        price_lists = (ListView) findViewById(R.id.listPrices);
        list_names = new ArrayList<>();
        list_keys = new ArrayList<>();
        list_prices = new ArrayList<>();

        adapter = new ChatAdapter(this);
        adp=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list_prices);
        lists.setAdapter(adapter);
        price_lists.setAdapter(adp);


        dbHelper = new FirebaseDatabaseHelper();
        //populate();
        lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListsActivity.this, ListActivity.class);
                intent.putExtra("ListKey", list_names.get(position));
                //intent.putExtra("Key",list_keys.get(position));
                startActivity(intent);
            }
        });

        lists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListsActivity.this);
                builder.setMessage(R.string.delete_message)

                        .setTitle(R.string.delete_title)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dbHelper.deleteList(list_keys.get(position), new FirebaseDatabaseHelper.listDataStatus() {
                                    @Override
                                    public void DataIsLoaded(ArrayList<List> lists, ArrayList<String> keys) {
                                        list_names.clear();
                                        list_keys.clear();
                                        for (int i = 0; i<lists.size(); i++) {
                                            list_names.add(lists.get(i).getName());
                                            list_keys.add(keys.get(i));
                                        }
                                        adapter.notifyDataSetChanged();
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
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        })
                        .show();

                return true;
            }
        });

        Toolbar toolbar_lists = (Toolbar) findViewById(R.id.toolbar_lists);
        toolbar_lists.setTitle("Lists");
        setSupportActionBar(toolbar_lists);

        dbHelper = new FirebaseDatabaseHelper();

        //populate();
    }

    @Override
    public void onResume() {
        super.onResume();
        populate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_lists, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_list:
                Intent intent = new Intent(this, CreateListActivity.class);
                intent.putExtra("Item", item.toString());
                startActivity(intent);
                //list_names.add("placeholder");
                //adapter.notifyDataSetChanged();
                return true;
            case R.id.About:
                LayoutInflater inflater = ListsActivity.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.custom_dialog,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(ListsActivity.this);
                builder.setView(view);
                builder.setTitle("Help");
                TextView instruction = view.findViewById(R.id.instruction) ;
                instruction.setText("Instrction:\nClick + button to create new group list");
                TextView version = view.findViewById(R.id.version) ;
                String version_name = com.google.firebase.BuildConfig.VERSION_NAME;
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

    private void populate() {
        dbHelper.readLists(new FirebaseDatabaseHelper.listDataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<List> lists, ArrayList<String> keys) {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                list_names.clear();
                list_keys.clear();
                list_prices.clear();
                for (int i = 0; i<lists.size(); i++) {
                    list_names.add(lists.get(i).getName());
                    list_keys.add(keys.get(i));
                    final String the_list=list_names.get(i);
                    //Log.i("Alert",the_list);
                    if(the_list != null){


                    dbHelper.setList(the_list);
                    dbHelper.readItems(new FirebaseDatabaseHelper.ItemDataStatus() {


                        @Override
                        public void DataIsLoaded(ArrayList<Item> items, ArrayList<String> keys) {

                            Log.i("Warning","Populating table");

                            int costPer=0;
                            for (int t = 0; t<items.size(); t++) {
                                Log.i("Testing",items.get(t).getName());
                                costPer+=items.get(t).getCost();
                            Log.i(the_list, String.valueOf(costPer));
//                        list_keys.add(keys.get(i));
                                adapter.notifyDataSetChanged();


                            }
                            list_prices.add("Price: "+String.valueOf(costPer));
                            adp.notifyDataSetChanged();
                            //list_names.set(list_names.indexOf(the_list),list_names.get(list_names.indexOf(the_list))+"     Price:"+String.valueOf(costPer));

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
                }
                adapter.notifyDataSetChanged();
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
    
    private class ChatAdapter extends ArrayAdapter<String>{

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        /**
         * Gets the size of list_names.
         *
         * @return Size of list_names.
         */
        public int getCount() {
            return list_names.size();
        }

        /**
         * Gets an item from the array by index.
         *
         * @param position Index of array to get item from.
         * @return Item.
         */
        public String getItem(int position) {
            return list_names.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ListsActivity.this.getLayoutInflater();

            View view = inflater.inflate(R.layout.layout_lists, null);

            TextView title = (TextView) view.findViewById(R.id.list_title);
            title.setText(getItem(position));

            return view;
        }
    }

}