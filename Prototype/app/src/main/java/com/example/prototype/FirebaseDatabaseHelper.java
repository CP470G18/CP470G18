package com.example.prototype;

import android.content.ClipData;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase Database;
    private DatabaseReference ReferenceItem;
    private DatabaseReference ReferenceList;
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<List> lists = new ArrayList<>();

    public FirebaseDatabaseHelper() {
        Database = FirebaseDatabase.getInstance();
        ReferenceList = Database.getReference("lists");//list of item list

    }

    public interface ItemDataStatus{
        void DataIsLoaded(ArrayList<Item> items, ArrayList<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }
    public interface listDataStatus{
        void DataIsLoaded(ArrayList<List> lists, ArrayList<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public void readItems(String listKey, final ItemDataStatus dataStatus){
        ReferenceItem = Database.getReference("lists").child(listKey).child("items");
        ReferenceItem.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                ArrayList<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : snapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Item item = keyNode.getValue(Item.class);
                    items.add(item);
                }
                dataStatus.DataIsLoaded(items, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void readLists(final listDataStatus dataStatus){
        ReferenceList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lists.clear();
                ArrayList<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : snapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    List list = keyNode.getValue(List.class);
                    lists.add(list);
                }
                dataStatus.DataIsLoaded(lists, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateItem(String listKey, String key, Item item ,final ItemDataStatus dataStatus){
        ReferenceItem = Database.getReference("lists").child(listKey).child("items");
        ReferenceItem.child(key).setValue(item).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsUpdated();
            }
        });
    }

    public void deleteItem(String listKey, String key, final ItemDataStatus dataStatus){
        ReferenceItem = Database.getReference("lists").child(listKey).child("items");
        ReferenceItem.child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsDeleted();
            }
        });
    }


    public void addItem(Item item, String listKey, final ItemDataStatus dataStatus){
        ReferenceItem = Database.getReference("lists").child(listKey).child("items");
        String key = ReferenceItem.push().getKey();
        ReferenceItem.child(key).setValue(item)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsInserted();
                    }
                });
    }

    public void addList(List list, final listDataStatus dataStatus){
        String key = ReferenceList.push().getKey();
        ReferenceList.child(key).setValue(list)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsInserted();
                    }
                });
    }

    public void deleteList(String key, final listDataStatus dataStatus) {
        ReferenceList.child(key).removeValue();
    }
}
