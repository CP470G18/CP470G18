package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    ArrayList<String> temp_store;
    ListView the_list;
    ArrayAdapter<String> adapter;
    androidx.appcompat.widget.Toolbar the_tool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        the_list= (ListView) findViewById(R.id.list);
        the_tool=(androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_list);
        temp_store=new ArrayList<String>();
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,temp_store);
        the_list.setAdapter(adapter);
        the_tool.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this,CreateItemActvity.class);
                startActivityForResult(intent, 2);
            }
        });
        the_list.setClickable(true);
        the_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle args = new Bundle();
                args.putString("message",temp_store.get(position));
                args.putLong("the_id",position);
//                if(bop==true){
//                    args.putString("phone","no");
//                    MessageFragment mf = new MessageFragment();
//                    mf.setArguments(args);
//                    mf.setElements(args);
//                    FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
//
//                    ft.add(R.id.frameview,mf);
//                    ft.commit();
//                }
//                else{
                    args.putString("phone","yes");
                    Intent intent = new Intent(ListActivity.this,ItemDetails.class);
                    intent.putExtra("bundle",args);
                    startActivityForResult(intent,2);
               // }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(resultCode==-1)
        {
            String message=data.getStringExtra("Response");
            Log.i("The test",message);
            temp_store.add(message);
            adapter.notifyDataSetChanged();

        }
        else{
            temp_store.remove(resultCode);
            adapter.notifyDataSetChanged();
        }
    }


}