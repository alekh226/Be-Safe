package project226.a000webhostapp.com.feelsecure;

import android.*;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng otherslocation;
    private LatLng currentLocation;
    private String place;
    private String otherKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Bundle getargs = getIntent().getExtras();
        //username = getargs.getString("username");
        //Log.d("TTTTTT","k"+username);
       // otherslocation = new LatLng(getargs.getDouble("otherLat"),getargs.getDouble("otherLong"));
        currentLocation = new LatLng(getargs.getDouble("currentLat"),getargs.getDouble("currentLong"));
        otherslocation = new LatLng(getargs.getDouble("otherLat"),getargs.getDouble("otherLong"));
        place=getargs.getString("place");
        otherKey=getargs.getString("otherKey");


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getOtherUserLocation(otherKey);
        // Add a marker in Sydney and move the camera
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    public void getOtherUserLocation(final String keyPassed){
                mMap.clear();
                String key1 = keyPassed;
                if (!key1.equals("X")) {
                    // while (true){
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference().child(key1);
                    // while (true){
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            //                String value = dataSnapshot.getValue().toString()
                            Double latitude = Double.valueOf((Double) dataSnapshot.child("Latitude").getValue());
                            Double longitude = Double.valueOf((Double) dataSnapshot.child("Longitude").getValue());
                            String userName = String.valueOf(dataSnapshot.child("userName").getValue());
                            Log.d("GetUsers", "Value is: " + userName + latitude + ":::" + longitude);
                            otherslocation = new LatLng(latitude, longitude);



                            ///////////////////////////////////////////////////////////////////////////////////
                            Object dataTransfer[] = new Object[5];
                            String url = getDirectionsUrl();
                            GetDirectionsData getDirectionsData = new GetDirectionsData();
                            dataTransfer[0] = mMap;
                            dataTransfer[1] = url;
                            dataTransfer[2] = new LatLng(otherslocation.latitude, otherslocation.longitude);
                            dataTransfer[3] = userName;
                            dataTransfer[4] = new LatLng(currentLocation.latitude, currentLocation.longitude);
                            getDirectionsData.execute(dataTransfer);
                            ///////////////////////////////////////////////////////////////////////////////////////
                                /*if (marker != null)
                                    marker.remove();
                                marker =addMarker(otherslocation,userName,distance[0]);
                                Log.d("ReadBothLocation",""+currentLocation+"kkk:"+ otherslocation);*/

                        }



                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w("tag", "Failed to read value.", error.toException());
                        }
                    });
                }else {
                    //mMap.clear();
                    Object dataTransfer[] = new Object[5];
                    String url = getDirectionsUrl();
                    GetDirectionsData getDirectionsData = new GetDirectionsData();
                    dataTransfer[0] = mMap;
                    dataTransfer[1] = url;
                    dataTransfer[2] = new LatLng(otherslocation.latitude, otherslocation.longitude);
                    dataTransfer[3] = place;
                    dataTransfer[4] = new LatLng(currentLocation.latitude, currentLocation.longitude);
                    getDirectionsData.execute(dataTransfer);
                }


    }
    private String getDirectionsUrl() {
        StringBuilder googleDirectionsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        googleDirectionsUrl.append("origin=" + currentLocation.latitude + "," + currentLocation.longitude);
        googleDirectionsUrl.append("&destination=" + otherslocation.latitude + "," + otherslocation.longitude);
        googleDirectionsUrl.append("&key=" + "AIzaSyBMlQHqPHOeQosA0Yuc9rrzk7wtwDCdUWk");

        return googleDirectionsUrl.toString();
    }
}
