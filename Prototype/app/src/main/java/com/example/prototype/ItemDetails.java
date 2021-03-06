package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ItemDetails extends AppCompatActivity {


    String messageText;
    String priceText;
    String descText;
    String listKey;
    String key;
    Bundle the_args;

    private Button editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_item_details);
        setContentView(R.layout.activity_item_details);


       // FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
         ListFragment mf = new ListFragment();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        Intent intent = getIntent();
        final Bundle bd = intent.getBundleExtra("bundle");
        Log.i("this is what",bd.getString("message"));

        setElements(bd);
        mf.setElements(bd);
        mf.setArguments(bd);
        //ft.add(R.id.the_frame,mf);

        ft.replace(R.id.listfragmentlayout, mf);

        ft.commit();

        editButton = (Button) findViewById(R.id.button3);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemDetails.this,UpdateDeleteItemActivity.class);
                intent.putExtra("list_key",listKey);
                intent.putExtra("description",descText);
                intent.putExtra("price",priceText);
                intent.putExtra("key",key);
                intent.putExtra("name", messageText);

                startActivityForResult(intent, 2);
                finish();
            }
        });
    }

    public void setElements(Bundle args){
        messageText=args.getString("message");
        //textId=args.getLong("the_id");
        priceText=args.getString("Price");
        descText=args.getString("Desc");
        listKey=args.getString("listKey");
        key = args.getString("key");
        the_args=args;
    }
}