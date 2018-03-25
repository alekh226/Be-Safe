package project226.a000webhostapp.com.feelsecure;

import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by WINDOWS on 3/10/2018.
 */

public class AsyncClass extends AsyncTask{
    public  LatLng latLng;
    public int i =0;
    private Boolean mLocationPermissionsGranted = true;
    private static final String TAG = "ASSYNC";
    private static final float DEFAULT_ZOOM = 15f;
    private static String username ;
    private static String key ;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    protected Object doInBackground(Object[] objects) {
        username =(String)objects[1];
        key =(String)objects[2];
        while (true){
            Log.d(TAG, "getDeviceLocation: getting the devices current location");

            mFusedLocationProviderClient = (FusedLocationProviderClient) objects[0];

            try{
                if(mLocationPermissionsGranted){

                    final Task location = mFusedLocationProviderClient.getLastLocation();
                    location.addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){

                                Location currentLocation = (Location) task.getResult();
                                //Log.d(TAG, "onComplete: found location!"+currentLocation.getLatitude()+currentLocation.getLongitude());
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference().child(key);
                                myRef.child("Latitude").setValue(currentLocation.getLatitude());
                                myRef.child("Longitude").setValue(currentLocation.getLongitude());
                                //moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                //     DEFAULT_ZOOM);

                            }else{
                                Log.d(TAG, "onComplete: current location is null");
                                //Toast.makeText(MainActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }catch (SecurityException e){
                Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
            }

            try {
                Thread.sleep(1111);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
         //return null;
    }
}
