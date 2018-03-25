package project226.a000webhostapp.com.feelsecure;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WINDOWS on 3/24/2018.
 */

public class SendMessage {
    private Context context;
    public SendMessage(Context context1){
        context=context1;
        List<String> keys =new ArrayList<String>();
        FirebaseDatabase database =FirebaseDatabase.getInstance();

        MyDBHandler myDBHandler =new MyDBHandler(context,null,null,1);
        keys=myDBHandler.getKeyList();
        String message = "It's Emergency!! I am in problem. Meet me at my Location";
        for (int i=0 ;i<keys.size();i++){
            DatabaseReference myRef =database.getReference(keys.get(i)).child("Message").push();
            myRef.child("status").setValue(true);
            myRef.child("text").setValue(message);
            myRef.child("key").setValue(myDBHandler.getUserKey());
            myRef.child("userName").setValue(myDBHandler.getUserName());
        }

    }


}
