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
import android.widget.Toast;

import java.util.List;

public class CreateItemActvity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "CreateItemActivity";
    private EditText name;
    private EditText cost;
    private EditText description;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item_actvity);

        final EditText text = findViewById(R.id.editTextTextPersonName);
        Button b = (Button) findViewById(R.id.button);
        name = (EditText) findViewById(R.id.editTextTextPersonName);
        description = (EditText) findViewById(R.id.editTextTextDescription);
        cost= (EditText) findViewById(R.id.editTextTextCost);
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("Response",text.getText().toString());
                resultIntent.putExtra("Price",cost.getText().toString());
                resultIntent.putExtra("Desc",description.getText().toString());
                setResult(-1, resultIntent);
                Item item = new Item();
                item.setName(name.getText().toString());
                item.setCost(Integer.valueOf(cost.getText().toString()));
                item.setDescription(description.getText().toString());

                new FirebaseDatabaseHelper().addItem(item, new FirebaseDatabaseHelper.DataStatus() {
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