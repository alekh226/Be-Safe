package project226.a000webhostapp.com.feelsecure;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.Manifest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rishabh on 3/16/2018.
 */

public class getContactDetails {
    public String email1 ="abc@jhk.lol";
    public String number="58585858";
    private LatLng latLng;
    private Context context;

    public getContactDetails(Context context1, LatLng  latLng1){
        context =context1;
        latLng =latLng1;

    }

    public void contact(){

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    email1=jsonResponse.getString("email");
                   String number1 =jsonResponse.getString("phone");


                    if (success) {

                        number  =number1;
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Error Occured")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        getContactDetailsRequest getContactDetailsRequest1 =new getContactDetailsRequest(latLng,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(getContactDetailsRequest1);
    }

}
