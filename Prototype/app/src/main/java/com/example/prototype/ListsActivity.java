package com.example.prototype;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import java.util.ArrayList;

public class ListsActivity extends AppCompatActivity {

    ListView lists;

    ArrayList<String> list_names;
    ChatAdapter adapter;

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        lists = (ListView) findViewById(R.id.lists);
        list_names = new ArrayList<>();

        adapter = new ChatAdapter(this);
        lists.setAdapter(adapter);

        lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListsActivity.this, ListActivity.class);
                intent.putExtra("List", list_names.get(position));
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
                                list_names.remove(position);
                                adapter.notifyDataSetChanged();
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
                //Intent intent = new Intent(this, CreateListactivity.class);
                //startActivityForResult(intent, 10);
                list_names.add("placeholder");
                adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            String title = data.getStringExtra("Title");
            list_names.add(title);
        }
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