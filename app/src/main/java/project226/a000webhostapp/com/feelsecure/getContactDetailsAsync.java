package project226.a000webhostapp.com.feelsecure;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Windows on 3/16/2018.
 */

public class getContactDetailsAsync extends AsyncTask {

    private Context context;
    private LatLng latLng;
    private  ProgressDialog progressDialog;
    private String what;
    private String snippet;
    private String number="0";
    private String email;
    private getContactDetails getContactDetails1;
    public getContactDetailsAsync(Context context1, LatLng latLng1,String what1,String snip){
        context =context1;
        latLng =latLng1;
        what = what1;
        snippet=snip;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        if (snippet.equals("psh")) {
                getContactDetails1 = new getContactDetails(context, latLng);
                getContactDetails1.contact();
                while (getContactDetails1.number.equals("58585858")) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }else {
            email=getEmail(snippet);
            number=getNumber(snippet);
            while (number.equals("0")) {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        /*progressDialog = new ProgressDialog((Activity)context);
        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(true);
        progressDialog.show();*/
    }

    @Override
    protected void onPostExecute(Object o) {
        //super.onPostExecute(o);
       // progressDialog.dismiss();
        Log.d("Driver","snippest:"+snippet);
        if(what.equals("call")){
            if (snippet.equals("psh")){
                number=getContactDetails1.number;
            }

            final int REQUEST_PHONE_CALL = 1;
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+number));
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(context,callIntent,null);
            // checking permission before placing a call.....//while combining please chage the Mainactivy...or see Android stdio phone call 2017
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(ContextCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions((Activity)context,new String[]{android.Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                }
            }
        }
        else if (  what.equals("email")){
            if (snippet.equals("psh")){
                email=getContactDetails1.email1;
            }
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto",email, null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
            emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(context,emailIntent,null);


        }
    }

    public String getNumber(String key){
        Log.d("Driver123","Key:"+key);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Driver");
        myRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Driver123","inside ondatachange");
                number=String.valueOf((String)dataSnapshot.child("phone").getValue());
                Log.d("Driver123","number:"+number);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Driver123","error:"+databaseError);
            }
        });
        Log.d("Driver123","number2:"+number);
    return number;
    }
    public String getEmail(String key){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Driver");
        myRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                email=String.valueOf((String)dataSnapshot.child("email").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return email;
    }
}
