package project226.a000webhostapp.com.feelsecure;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Windows on 3/16/2018.
 */

public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {
public LatLng nearestHospital;
public LatLng nearestPS;
private String googlePlacesData;
private Context  context;
private LatLng currentLocation;
private String place;
private GoogleMap mMap;
private String placeNameN ;
private String vicinityN;
private boolean flag;
        String url;
public GetNearbyPlacesData(Context co,LatLng cl,String place1,Boolean flag1){
        context =co;
        currentLocation =cl;
        place=place1;
        flag=flag1;
}

@Override
protected String doInBackground(Object... objects){
        mMap = (GoogleMap)objects[0];
        url = (String)objects[1];

        DownloadUrl downloadURL = new DownloadUrl();
        try {
        googlePlacesData = downloadURL.readUrl(url);
        } catch (IOException e) {
        e.printStackTrace();
        }

        return googlePlacesData;
        }

@Override
protected void onPostExecute(String s){

        List<HashMap<String, String>> nearbyPlaceList;
        DataParser parser = new DataParser();
        nearbyPlaceList = parser.parse(s);
        Log.d("nearbyplacesdata","called parse method");
        showNearbyPlaces(nearbyPlaceList);



        }

private void showNearbyPlaces(List<HashMap<String, String>> nearbyPlaceList){
                float temp=11000;
                float temp1=11000;
        for(int i = 0; i < nearbyPlaceList.size(); i++){

                MarkerOptions markerOptions = new MarkerOptions();
                HashMap<String, String> googlePlace = nearbyPlaceList.get(i);

                String placeName = googlePlace.get("place_name");
                String vicinity = googlePlace.get("vicinity");
                double lat = Double.parseDouble( googlePlace.get("lat"));
                double lng = Double.parseDouble( googlePlace.get("lng"));
                float[]result = new float[5];
               Location.distanceBetween(currentLocation.latitude,currentLocation.longitude,lat,lng,result);

                LatLng latLng = new LatLng( lat, lng);
            Log.d("DistanceBetween",result[0]+""+place+latLng);
                if(result[0]<temp && place.equals("hospital")){
                    temp= result[0];
                    nearestHospital =latLng;
                    placeNameN=placeName;
                    vicinityN=vicinity;
                }
                else if (result[0]<temp1 && place.equals("police")){
                    temp1=result[0];
                    nearestPS = latLng;
                    placeNameN=placeName;
                    vicinityN=vicinity;
                }
                if (flag) {
                    markerOptions.position(latLng);
                    markerOptions.title(placeName + " : " + vicinity)
                            .snippet("psh");
                   //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                   if (place.equals("hospital")) {
                       markerOptions .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_h));
                    }
                    else if (place.equals("police"))
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_ps));
                    mMap.addMarker(markerOptions);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
                }

                }

                if(!flag){
                    if(place.equals("police")) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(nearestPS);
                        markerOptions.title(placeNameN + " : " + vicinityN)
                                .snippet("psh");
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_ps));
                         //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                        mMap.addMarker(markerOptions);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
                    }
                    if(place.equals("hospital")) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(nearestHospital);
                        markerOptions.title(placeNameN + " : " + vicinityN)
                                .snippet("psh");
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_h));
                        //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                        mMap.addMarker(markerOptions);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
                    }

                }
    Log.d("DistanceBetween","neareest "+place+nearestPS+"lll"+nearestHospital);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                        Contact contact = Contact.newInstance(marker.getTitle(),marker.getPosition().latitude,
                                marker.getPosition().longitude,marker.getSnippet(),
                                currentLocation.latitude,currentLocation.longitude);
                        FragmentManager fragmentManager =((FragmentActivity)context).getSupportFragmentManager();
                        fragmentManager.popBackStackImmediate();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.add(R.id.map,contact);
                        fragmentTransaction.commit();
                        Log.d("MarkerLatLang","kkkh"+marker.getPosition());

                        return true;
                }
        });

        }
        public LatLng getNearestPS(){
            return nearestPS;
        }
        public LatLng  getNearestPlace (String s){
                String vicinity =null;
                String placeName = null;
                LatLng latLng=null;
                List<HashMap<String, String>> nearbyPlaceList;
                DataParser parser = new DataParser();
                nearbyPlaceList = parser.parse(s);
                Log.d("nearbyplacesdata","called parse method");

                MarkerOptions markerOptions = new MarkerOptions();
                float temp=1000000;
                for(int i = 0; i < nearbyPlaceList.size(); i++){


                        HashMap<String, String> googlePlace = nearbyPlaceList.get(i);


                        double lat = Double.parseDouble( googlePlace.get("lat"));
                        double lng = Double.parseDouble( googlePlace.get("lng"));
                        float[]result = new float[0];
                        Location.distanceBetween(currentLocation.latitude,currentLocation.longitude,lat,lng,result);
                        latLng = new LatLng( lat, lng);
                        if(result[0]<temp){
                                temp=result[0];
                                placeName = googlePlace.get("place_name");
                                vicinity = googlePlace.get("vicinity");
                        }


                }
                markerOptions.position(latLng);
                markerOptions.title(placeName + " : "+ vicinity)
                        .snippet("psh");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

                mMap.addMarker(markerOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
                return latLng;

        }
}
