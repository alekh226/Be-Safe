package project226.a000webhostapp.com.feelsecure;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by WINDOWS on 3/26/2018.
 */

public class GetDirection {
    private GoogleMap mMap;
    private LatLng otherslocation;
    private LatLng currentLocation;
    private String userName;
    public void GetDirection(GoogleMap googleMap,LatLng latLngo,LatLng latLngc,String s){
        mMap=googleMap;
        otherslocation=latLngo;
        currentLocation=latLngc;
        userName=s;
    }

    public void getdir(){
        Object dataTransfer[] = new Object[5];
        String url = getDirectionsUrl();
        GetDirectionsData getDirectionsData = new GetDirectionsData();
        dataTransfer[0] = mMap;
        dataTransfer[1] = url;
        dataTransfer[2] = new LatLng(otherslocation.latitude, otherslocation.longitude);
        dataTransfer[3] = userName;
        dataTransfer[4]= new LatLng(currentLocation.latitude,currentLocation.longitude);
        getDirectionsData.execute(dataTransfer);
    }
    private String getDirectionsUrl()
    {
        StringBuilder googleDirectionsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        googleDirectionsUrl.append("origin="+currentLocation.latitude+","+currentLocation.longitude);
        googleDirectionsUrl.append("&destination="+otherslocation.latitude+","+otherslocation.longitude);
        googleDirectionsUrl.append("&key="+"AIzaSyBMlQHqPHOeQosA0Yuc9rrzk7wtwDCdUWk");

        return googleDirectionsUrl.toString();
    }
}
