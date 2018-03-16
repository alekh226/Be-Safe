package project226.a000webhostapp.com.feelsecure;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,OnMapReadyCallback {
    public Location currentLocation=null;
    public LatLng otherslocation;
    private static String otherKey = "K";
    private Marker marker;

    private GoogleMap mMap;

    private static  final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private  static  final  String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private Boolean mLocationPermissionsGranted = false;
    private static final String TAG = "MainActivity";
    private  static  final  float DEFAULT_ZOOM = 15f;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private ArrayList<String> crimeLabel= new ArrayList<String>();
    private ArrayList<String > crimeCount = new ArrayList<String>();
    private Location LocationForall;
    public  int counter=55;
    public static String username = "Alekh";
    public static String key ="XYZ";
    private static  String addKey;
    private MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserList userList =new UserList();
        initMap();
        getDeviceLocation();
        getLocationPermission();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                /*DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

//To Open:
                drawerLayout.openDrawer(Gravity.START);*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //setContentView(R.layout.content_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //MyDBHandler myDBHandler = new MyDBHandler(MainActivity.this,null,null,1);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();




        //configure initial path
        myRef.child(key);
        myRef = database.getReference().child(key);
        myRef.child("userName").setValue(username);
        myRef.child("accessList");
        //Read from firebase
       /* myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue().toString();
                Object latitude =  dataSnapshot.getValue();
                Log.d("firebaseStatus", "Value is: " + latitude);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });*/


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        /*int backStackEntry = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntry > 0) {
            for (int i = 0; i < backStackEntry-2; i++) {
                getSupportFragmentManager().popBackStackImmediate();
            }
        }*/

        if (getSupportFragmentManager().getFragments() != null && getSupportFragmentManager().getFragments().size() > 0) {
            for (int i = getSupportFragmentManager().getFragments().size()-1;i>0; i--) {
                Fragment mFragment = getSupportFragmentManager().getFragments().get(i);
                if (mFragment != null) {
                    getSupportFragmentManager().beginTransaction().remove(mFragment).commit();
                }
            }

        }

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.PoliceStations) {
            /*setContentView(R.layout.activity_maps);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            B
            // Handle the camera action*/

        } else if (id == R.id.Medicals) {
            if (otherKey.equals("K")){
                Bundle getargs = getIntent().getExtras();
                //username = getargs.getString("username");
                Log.d("TTTTTT","k");
                otherKey =getargs.getString("otherKey");}
            Toast.makeText(MainActivity.this,"gallery",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.Ambulance) {


        } else if (id == R.id.Location) {

            SelectUser selectUser =new SelectUser();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStackImmediate();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.map,selectUser);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.Crime) {

            LatLng latLng = new LatLng(LocationForall.getLatitude(),LocationForall.getLongitude());
            AsyncTask task =new getLocationAddressAsync(MainActivity.this);
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,latLng);
            /*new Thread(new Runnable() {
                @Override
                public void run() {
                    LatLng latLng = new LatLng(LocationForall.getLatitude(),LocationForall.getLongitude());
                    final GetCrimeDetails getCrimeDetails =new GetCrimeDetails();
                    String krime =getCrimeDetails.getLocationAddress(MainActivity.this,latLng);//getLocationAddress(latLng);
                    try {
                        Thread.sleep(6000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(krime.equals("Ernakulam"))
                        krime ="Ernakulam Rural";
                    // searchCrime(krime);
                    getCrimeDetails.searchCrime(MainActivity.this,krime);

                    try {
                        Thread.sleep(6000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    CrimeFragment crimeFragment =CrimeFragment.newInstance(getCrimeDetails.crimeLabel,getCrimeDetails.crimeCount);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    //fragmentManager.popBackStackImmediate();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.addToBackStack("Second");
                    fragmentTransaction.add(R.id.map,crimeFragment);
                    fragmentTransaction.commit();

                }
            }).start();*/
            /*CrimeFragment crimeFragment= new CrimeFragment();
            Bundle bd =new Bundle();
            bd.putString("record",krime);
            crimeFragment.setArguments(bd);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.map,crimeFragment).commit();*/
        } else if (id
                == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        // Add a marker in Sydney and move the camera

        getDeviceLocation();
      //  getOtherUserLocation(otherKey);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
    }



    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                Log.d(TAG, "getLocationPermission:location permissions granted");

                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }
    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        AsyncTask task =new AsyncClass();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,mFusedLocationProviderClient,username,key);

        try {
            if (mLocationPermissionsGranted) {

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");
                            currentLocation = (Location) task.getResult();
                            LocationForall=currentLocation;
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM);

                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MainActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    public Marker addMarker(LatLng latLng,String title,float result){

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latLng.latitude,latLng.longitude);
        Marker marker =mMap.addMarker(new MarkerOptions().position(sydney).title(title).snippet("Distance="+result));

        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        return marker;

    }





    public void addNewUserClicked(View view){
        AddUser newUser =AddUser.newInstance(key,"prams2");
        SelectUser selectUser =new SelectUser();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStackImmediate();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(selectUser);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(R.id.map,newUser);
        fragmentTransaction.commit();

    }
    public void existingUserClicked(View view){
        UserList userList= UserList.newInstance(currentLocation.getLatitude(),currentLocation.getLongitude());
        SelectUser selectUser =new SelectUser();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStackImmediate();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(selectUser);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(R.id.map,userList);
        fragmentTransaction.commit();
    }

    public void shareKeyClicked(View view){
        String whatsAppMessage = "This Is my Key :XYZ";

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        String title = getResources().getString(R.string.chooser_title);
        sendIntent.putExtra(Intent.EXTRA_TEXT, whatsAppMessage);
        sendIntent.setType("text/plain");
        Intent chooser = Intent.createChooser(sendIntent, title);
        // Do not forget to add this to open whatsApp App specifically
        /*sendIntent.setPackage("com.whatsapp");
        sendIntent.setPackage("com.bsb.hike");
        startActivity(sendIntent);*/
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }

}
