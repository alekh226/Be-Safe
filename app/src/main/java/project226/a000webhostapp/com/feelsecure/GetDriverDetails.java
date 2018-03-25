package project226.a000webhostapp.com.feelsecure;

import android.content.Context;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by WINDOWS on 3/24/2018.
 */

public class GetDriverDetails {
    public String name;
    public String email;
    public String phone;
    public LatLng nearestDriverLocation;
    private LatLng currentLocation;
    private Context context;
    private String driverId;
    private GoogleMap mMap;
    public GetDriverDetails(Context context1,Object o,LatLng latLng){
        currentLocation =latLng;
        context=context1;
        mMap=(GoogleMap)o;
    }

    public void  getDetails(final boolean flag){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Driver");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                float temp=110000000;
                mMap.clear();
                MarkerOptions markerOptions = new MarkerOptions();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Double latitude =Double.valueOf((Double) dataSnapshot1.child("latitude").getValue());
                    Double longitude =Double.valueOf((Double) dataSnapshot1.child("longitude").getValue());
                    String namei =String.valueOf((String) dataSnapshot1.child("name").getValue());
                    float[]result = new float[5];
                    Location.distanceBetween(currentLocation.latitude,currentLocation.longitude,latitude,longitude,result);
                    if(result[0]<temp ){
                        temp= result[0];
                        email = String.valueOf((String)dataSnapshot1.child("email").getValue());
                        phone =String.valueOf((String)dataSnapshot1.child("phone").getValue());
                        nearestDriverLocation =new LatLng(latitude,longitude);
                        name =namei;
                        driverId =dataSnapshot1.getKey();
                    }
                    if(flag) {
                        markerOptions.position(new LatLng(latitude, longitude));
                        markerOptions.title("Driver Name : " + namei);
                        markerOptions .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_amb));
                        markerOptions.snippet(dataSnapshot1.getKey());
                        mMap.addMarker(markerOptions);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
                    }
                    else {
                        markerOptions.position(nearestDriverLocation);
                        markerOptions.title("Driver Name : " + name);
                        markerOptions .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_amb));
                        markerOptions.snippet(driverId);
                        mMap.addMarker(markerOptions);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
                    }
                }

                Log.d("Driver","nere"+nearestDriverLocation+name);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Driver", "Failed to read value.", error.toException());
            }
        });

        /*mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                /*Contact contact = Contact.newInstance(marker.getTitle(),marker.getPosition().latitude,marker.getPosition().longitude);
                FragmentManager fragmentManager =((FragmentActivity)context).getSupportFragmentManager();
                fragmentManager.popBackStackImmediate();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.add(R.id.map,contact);
                fragmentTransaction.commit();
                Log.d("MarkerLatLang","kkkh"+marker.getPosition());

                return true;
            }
        });*/
    }



}
