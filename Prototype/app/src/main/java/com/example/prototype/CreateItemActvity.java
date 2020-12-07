package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class CreateItemActvity extends AppCompatActivity {

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

        name = findViewById(R.id.editTextTextPersonName);
        cost = findViewById(R.id.editTextTextCost);
        description = findViewById(R.id.editTextTextDescription);
        submit = (Button) findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Item item = new Item();
                item.setName(name.getText().toString());
                item.setCost(Integer.valueOf(cost.getText().toString()));
                item.setDescription(description.getText().toString());

                new FirebaseDatabaseHelper().addItem(item, new FirebaseDatabaseHelper.ItemDataStatus(){
                    @Override
                    public void DataIsLoaded(List<Item> items, List<String> keys) {
                        Toast.makeText(CreateItemActvity.this, "Item Successfully saved", Toast.LENGTH_LONG).show();
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