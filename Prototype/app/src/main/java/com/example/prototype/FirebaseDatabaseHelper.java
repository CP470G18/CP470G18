package com.example.prototype;

import android.content.ClipData;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase Database;
    private DatabaseReference ReferenceItem;
    private DatabaseReference ReferenceGroup;
    private List<Item> items = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();

    public FirebaseDatabaseHelper() {
        Database = FirebaseDatabase.getInstance();
        ReferenceItem = Database.getReference("items");// for item list
        ReferenceGroup = Database.getReference("group");//list of item list
    }


    public interface ItemDataStatus{
        void DataIsLoaded(List<Item> items, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }
    public interface GroupDataStatus{
        void DataIsLoaded(List<Group> Groups, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public void readItems(final ItemDataStatus dataStatus){
        ReferenceItem.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                List<String> keys = new ArrayList<>();
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

    public void readGroups(final GroupDataStatus dataStatus){
        ReferenceGroup.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groups.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : snapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Group group = keyNode.getValue(Group.class);
                    groups.add(group);
                }
                dataStatus.DataIsLoaded(groups, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addItem(Item item, final ItemDataStatus dataStatus){
        String key = ReferenceItem.push().getKey();
        ReferenceItem.child(key).setValue(item)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsInserted();
                    }
                });
    }

    public void addGroup(Group group, final GroupDataStatus dataStatus){
        String key = ReferenceGroup.push().getKey();
        ReferenceGroup.child(key).setValue(group)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsInserted();
                    }
                });
    }
}
