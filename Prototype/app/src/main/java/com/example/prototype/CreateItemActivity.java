package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateItemActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "CreateItemActivity";
    private EditText name;
    private EditText cost;
    private EditText description;
    private Button submit;
    private Button Cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item_actvity);

        final EditText text = findViewById(R.id.editTextTextPersonName);
        submit = (Button) findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("Response",text.getText().toString());
                setResult(-1, resultIntent);
                Item item = new Item();

                name = findViewById(R.id.editTextTextPersonName);
                cost = findViewById(R.id.editTextTextCost);
                description = findViewById(R.id.editTextTextDescription);

                item.setName(name.getText().toString());
                item.setCost(Integer.valueOf(cost.getText().toString()));
                item.setDescription(description.getText().toString());

                String listName = getIntent().getExtras().getString("listName");
                new FirebaseDatabaseHelper().addItem(item, listName, new FirebaseDatabaseHelper.ItemDataStatus(){
                    @Override
                    public void DataIsLoaded(ArrayList<Item> items, ArrayList<String> keys) {
                        Toast.makeText(CreateItemActivity.this, "Item Successfully saved", Toast.LENGTH_LONG).show();
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
                finish();

            }
        });

        Cancel = (Button) findViewById(R.id.button2);
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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