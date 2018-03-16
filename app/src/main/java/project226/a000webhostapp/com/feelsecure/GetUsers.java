package project226.a000webhostapp.com.feelsecure;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by WINDOWS on 3/11/2018.
 */

public class GetUsers extends MainActivity{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    private static String TAG = "";
    public List <String> userListKey =new ArrayList<String>();
    public List <String> userList =new ArrayList<String>();
    public GetUsers(String key) {
         myRef = database.getReference(key);
        Log.d(TAG,"Constructor"+key);
    }

    public String addUser(final String key){

        final String[] userName = new String[1];
        myRef.child("accesList").push().setValue(key);
        DatabaseReference myRef2 = database.getReference();
        myRef2.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue().toString();
                 userName[0] =String.valueOf((String) dataSnapshot.child("userName").getValue());
                 MyDBHandler myDBHandler = new MyDBHandler(GetUsers.this,null,null,1);
                 myDBHandler.insertUsers(key,userName[0]);
                Log.d(TAG, "Value is: " + userName[0]);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return userName[0];
    }
   /* public List<String> getUsers(){
        //final String [] usersList1 =new String[100];


        //userListKey.clear();
        myRef.child("accesList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue().toString();
                int i=0;
                HashMap<String,String> kkk = null;

                kkk = (HashMap<String, String>) dataSnapshot.getValue();
                for (DataSnapshot children : dataSnapshot.getChildren()) {
                   // usersList[0] = String.valueOf((String) dataSnapshot.getValue());
                    userListKey.add(kkk.get(children.getKey()));
                   //usersList. =kkk.get(children.getKey());

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        Log.d(TAG, "Value is userList: " +userListKey+ userListKey.size());
        return userListKey;
    }

    public String userName(String key){

        final String[] userName = new String[1];
        DatabaseReference myRef2 = database.getReference();
        myRef2.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userName[0] =String.valueOf((String) dataSnapshot.child("userName").getValue());
                Log.d(TAG, "Value is: " + userName[0]);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return userName[0];
    }*/
}
