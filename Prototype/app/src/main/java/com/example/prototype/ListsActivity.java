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
import java.util.Collections;

public class ListsActivity extends AppCompatActivity {

    private ListView lists;

    private ArrayList<String> list_names;
    private ArrayList<String> list_keys;
    private ArrayAdapter<String> adapter;

    private FirebaseDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        lists = (ListView) findViewById(R.id.lists);
        list_names = new ArrayList<>();
        list_keys = new ArrayList<>();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list_names);
        lists.setAdapter(adapter);

        dbHelper = new FirebaseDatabaseHelper();
        lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListsActivity.this, ListActivity.class);
                intent.putExtra("Key",list_keys.get(position));
                intent.putExtra("Name", list_names.get(position));
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

    private void populate() {
        dbHelper.readLists(new FirebaseDatabaseHelper.listDataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<List> lists, ArrayList<String> keys) {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                list_names.clear();
                list_keys.clear();
                for (int i = 0; i<lists.size(); i++) {
                    list_names.add(lists.get(i).getName());
                    list_keys.add(keys.get(i));
                    final String the_list=list_names.get(i);
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

    private void sortA() {
        for (int i = 1; i < list_names.size(); i++) {
            String currentName = list_names.get(i);
            String currentKey = list_keys.get(i);
            int j = i - 1;
            while (j >= 0 && currentName.compareToIgnoreCase(list_names.get(j)) < 0) {
                list_names.set(j+1, list_names.get(j));
                list_keys.set(j+1, list_keys.get(j));
                j--;
            }
            list_names.set(j+1, currentName);
            list_keys.set(j+1, currentKey);
        }
    }

    private void sortB() {
        sortA();
        Collections.reverse(list_names);
        Collections.reverse(list_keys);
    }
}