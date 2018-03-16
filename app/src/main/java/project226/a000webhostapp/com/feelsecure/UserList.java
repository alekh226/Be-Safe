package project226.a000webhostapp.com.feelsecure;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;


public class UserList extends Fragment implements OnMapReadyCallback {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private double latitude;
    private double longitude;
    public EditText enterUserKey;
    public TextView textView;
    public Button addUserButton;
    private LatLng currentLocation;
    private GoogleMap mMap;
    MainActivity mainActivity;
    public static UserList newInstance(double latitude1,double longitude1) {
        UserList fragment = new UserList();
        Bundle args = new Bundle();
        args.putDouble(ARG_PARAM1, latitude1);
        args.putDouble(ARG_PARAM2, longitude1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            latitude = getArguments().getDouble(ARG_PARAM1);

            longitude = getArguments().getDouble(ARG_PARAM2);
            currentLocation=new LatLng(latitude,longitude);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_user_list, container, false);
       // FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        recyclerView = (RecyclerView) view.findViewById(R.id.userList);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        List<String> input = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        MyDBHandler myDBHandler = new MyDBHandler(getActivity().getApplicationContext(),null,null,1);
        input = myDBHandler.getUserNameList();
        keys=myDBHandler.getKeyList();
        mAdapter = new MyAdapter(getActivity().getApplicationContext(),input,keys,mMap,currentLocation);
        recyclerView.setAdapter(mAdapter);
        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap =googleMap;
    }

}
