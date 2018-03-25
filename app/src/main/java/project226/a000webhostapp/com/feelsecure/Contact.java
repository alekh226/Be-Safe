package project226.a000webhostapp.com.feelsecure;

import android.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;


public class Contact extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";

    // TODO: Rename and change types of parameters
    private String title;
    private String snippet;
    private Double latitude;
    private Double longitude;
    private Button call;
    private Button email;
    private TextView titleText;
    private LatLng  latLng;
    private LatLng currentLocation;
    private Button getDirection;

    public Contact() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Contact.
     */
    // TODO: Rename and change types and number of parameters
    public static Contact newInstance(String param1, Double param2,Double prams3,String prams4
                                        ,Double param5,Double prams6) {
        Contact fragment = new Contact();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putDouble(ARG_PARAM2, param2);
        args.putDouble(ARG_PARAM3, prams3);
        args.putString(ARG_PARAM4, prams4);
        args.putDouble(ARG_PARAM5, param5);
        args.putDouble(ARG_PARAM6, prams6);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM1);
            latitude = getArguments().getDouble(ARG_PARAM2);
            longitude =getArguments().getDouble(ARG_PARAM3);
            snippet=getArguments().getString(ARG_PARAM4);
            currentLocation=new LatLng(getArguments().getDouble(ARG_PARAM5),getArguments().getDouble(ARG_PARAM6));
            latLng =new LatLng(latitude,longitude);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_contact, container, false);
        titleText =(TextView)rootview.findViewById(R.id.title_contact);

        titleText.setText(title);
        call =(Button)rootview.findViewById(R.id.callButton);
        email =(Button)rootview.findViewById(R.id.emailButton);
        getDirection=(Button)rootview.findViewById(R.id.getDirButton);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*getContactDetails getContactDetails1 =new getContactDetails(getActivity().getApplicationContext(),latLng);
                getContactDetails1.contact();*/
                AsyncTask task = new getContactDetailsAsync(getActivity().getApplicationContext(),latLng,"call",snippet);
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask task = new getContactDetailsAsync(getActivity().getApplicationContext(),latLng,"email",snippet);
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(),MapsActivity2.class);
                Bundle bundle =new Bundle();
                bundle.putString("otherKey","X");
                bundle.putDouble("currentLat",currentLocation.latitude);
                bundle.putDouble("currentLong",currentLocation.longitude);
                bundle.putDouble("otherLat",latitude);
                bundle.putDouble("otherLong",longitude);
                bundle.putString("place",snippet);
                intent.putExtras(bundle);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        return rootview;
    }
}
