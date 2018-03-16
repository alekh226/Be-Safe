package project226.a000webhostapp.com.feelsecure;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.Polyline;

import com.google.maps.android.PolyUtil;

import org.json.JSONArray;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by WINDOWS on 3/10/2018.
 */

public class GetDirectionsData extends AsyncTask {
    GoogleMap mMap;
    String url;
    String googleDirectionsData;
    String duration, distance;
    LatLng latLng;
    LatLng currentLocation;
    String userName;
    @Override
    protected Object doInBackground(Object[] objects) {
        mMap = (GoogleMap)objects[0];
        url = (String)objects[1];
        latLng = (LatLng)objects[2];
        userName = (String)objects[3];
        currentLocation =(LatLng)objects[4];

        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googleDirectionsData = downloadUrl.readUrl(url);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return googleDirectionsData;
    }

    @Override
    protected void onPostExecute(Object o) {
        HashMap<String,String> durationDistnceList = null;
        String[] directionsList;
        DataParser parser = new DataParser();
        directionsList = parser.parseDirections(o.toString());
        Log.d("Parser",o.toString());
       durationDistnceList =parser.getDuration(parser.parseDuration(o.toString()));
       Log.d("Parser",parser.parseDuration(o.toString()).toString());
        duration = durationDistnceList.get("duration");
        distance =durationDistnceList.get("distance");
        displayMarker(latLng,currentLocation,duration,distance,userName);
        displayDirection(directionsList);
    }
    public void displayDirection(String[] directionsList)
    {
        Log.d("googleDirectionsData",""+directionsList.toString());
        int count = directionsList.length;
        for(int i = 0;i<count;i++)
        {
            PolylineOptions options = new PolylineOptions();
            options.color(Color.BLUE);
            options.width(10);
            options.addAll(PolyUtil.decode(directionsList[i]));

            mMap.addPolyline(options);
        }
    }
    public void displayMarker(LatLng latLng,LatLng currentLocation,String duration ,String distance,String userName){
        MarkerOptions markerOptions =new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(userName);
        markerOptions.snippet("Distance = "+distance+" Duration = "+ duration);
       // markerOptions.snippet("Duration = "+ duration);
        mMap.addMarker(markerOptions).showInfoWindow();
        CameraUpdate center=
                CameraUpdateFactory.newLatLng(new LatLng(latLng.latitude,
                        latLng.longitude));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(10);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
    }
}
