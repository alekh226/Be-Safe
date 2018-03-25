package project226.a000webhostapp.com.feelsecure;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WINDOWS on 3/24/2018.
 */

public class ReadMessage extends AsyncTask{

    private Context context;
    private TextView eMessageCount;
    private Model model;
    public ReadMessage(Context context1, TextView eMessageCount1,Model model1){
        context=context1;
        eMessageCount=eMessageCount1;
        model=model1;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        List<String> keys =new ArrayList<String>();
        MyDBHandler myDBHandler =new MyDBHandler(context,null,null,1);
        keys=myDBHandler.getKeyList();
        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference myRef =database.getReference(myDBHandler.getUserKey());

        myRef.child("Message").addChildEventListener(new ChildEventListener() {
            int count=0;
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                model.setTextMessage((String) dataSnapshot.child("text").getValue());
                model.setUser_name((String) dataSnapshot.child("userName").getValue());
                model.setKeys((String) dataSnapshot.child("key").getValue());
                NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
                        builder
                        .setContentTitle("EMERGENCY")
                        .setContentText("You have "+count+" Emergency message")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
                Log.d("Message123",dataSnapshot.child("status").getValue().toString());
                count++;
                eMessageCount.setText(""+count);
                Log.d("Message123","Text:"+model.getTextMessage().toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // Log.d("Message123",keys.toString());
        return null;
    }
}
