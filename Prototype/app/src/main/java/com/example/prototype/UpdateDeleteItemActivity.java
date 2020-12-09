package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class UpdateDeleteItemActivity extends AppCompatActivity {

    private EditText name;
    private EditText cost;
    private EditText description;
    private Button Update;
    private Button Delete;
    private Button Cancel;
    private Spinner item_spinner;
    private String key;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_item);

        name = (EditText) findViewById(R.id.editTextTextPersonName);
        description = (EditText) findViewById(R.id.editTextTextDescription);
        cost= (EditText) findViewById(R.id.editTextTextCost);
        Update = (Button) findViewById(R.id.button);
        Delete = (Button) findViewById(R.id.button);
        Cancel = (Button) findViewById(R.id.button);

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = new Item();
                item.setName(name.getText().toString());
                item.setDescription(description.getText().toString());
                item.setCost(Integer.valueOf(cost.getText().toString()));

                new FirebaseDatabaseHelper().updateItem(key, item, new FirebaseDatabaseHelper.ItemDataStatus() {
                    @Override
                    public void DataIsLoaded(ArrayList<Item> items, ArrayList<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(UpdateDeleteItemActivity.this, "Updated", Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
                finish();
            }

        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FirebaseDatabaseHelper().deleteItem(key, new FirebaseDatabaseHelper.ItemDataStatus() {
                    @Override
                    public void DataIsLoaded(ArrayList<Item> items, ArrayList<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {
                        Toast.makeText(UpdateDeleteItemActivity.this, "Deleted", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                });
            }
        });



    }


    private int SpinnerItem(Spinner spinner, String item){
        int index = 0;
        for(int i = 0; i < spinner.getCount(); i++){
            if(spinner.getItemAtPosition(i).equals(item)){
                index = i;
                break;
            }
        }
        return index;
    }
}