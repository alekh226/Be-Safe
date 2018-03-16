package project226.a000webhostapp.com.feelsecure;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by WINDOWS on 3/13/2018.
 */



public class GetCrimeDetails {
    public ArrayList<String> crimeLabel= new ArrayList<String>();
    public ArrayList<String > crimeCount = new ArrayList<String>();
    public String crimeRecord=null;
    public String searchCrime(final Context context, final String place){

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response1) {
                try {
                    JSONObject jsonResponse = new JSONObject(response1);
                    boolean success = jsonResponse.getBoolean("success");
                    final String district_idS=jsonResponse.getString("district_id");

                    while (!success);

                    if (success) {
                        Response.Listener<String>  responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    final JSONArray arr = jsonResponse.getJSONArray("data");

                                    final JSONArray field =jsonResponse.getJSONArray("fields");
                                    for(int i=0;i<field.length();i++){
                                        JSONObject label =field.getJSONObject(i);
                                        JSONArray arr1 = arr.getJSONArray(Integer.parseInt(district_idS));
                                        if(!arr1.getString(i).equals("0")) {
                                            crimeRecord += label.getString("label") + " : " + arr1.getString(i) + "\n";
                                            crimeLabel.add(label.getString("label"));
                                            crimeCount.add(arr1.getString(i));
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        };
                        CrimeRecordRequest crimeRecordRequest =new CrimeRecordRequest(responseListener);
                        RequestQueue queue = Volley.newRequestQueue(context);
                        queue.add(crimeRecordRequest);
                    } else {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                        builder.setMessage("Login Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        GetDistrictIdRequest getDistrictIdRequest=new GetDistrictIdRequest(place, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(getDistrictIdRequest);



        return crimeRecord;
    }

    public String getLocationAddress(Context context ,LatLng location){
        Log.d("getLocationAddress","location:"+location.toString());
        Geocoder geoCoder = new Geocoder(context, Locale.getDefault());
        String fnialAddress =null;
        try {
            List<Address> address = geoCoder.getFromLocation(location.latitude, location.longitude, 1);
            Log.d("getLocationAddress","address:"+address.get(0).getSubAdminArea());

            fnialAddress = address.get(0).getSubAdminArea(); //This is the complete address.
            Log.d("getLocationAddress","finalAddress:"+fnialAddress);

        } catch (Exception e) {
            // Handle IOException
        }
        return fnialAddress;
    }

    public String getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        String p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            Log.d("getLocationAddress","addresswwww:"+address);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            p1 = location.getSubAdminArea();
            Log.d("getLocationAddress","latlang:"+p1);
        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }
    public void searchButtonClickedForCrime(View view){
        SearchBar searchBar =new SearchBar();
        Log.d("SearchSearch",searchBar.address.getText().toString());
    }
    public LatLng getLocationFromAddressLatlang(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            Log.d("getLocationAddress","addresswwww:"+address);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(),location.getLongitude());
            Log.d("getLocationAddress","latlang:"+p1);
        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }
   
}


