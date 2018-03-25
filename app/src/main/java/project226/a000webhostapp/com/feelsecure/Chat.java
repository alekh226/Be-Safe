package project226.a000webhostapp.com.feelsecure;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class Chat extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";

    // TODO: Rename and change types of parameters
    private List<String> text;
    private List<String> keysSet;
    private List<String> userName;
    private LatLng currentLocation;

    public Chat() {
        // Required empty public constructor
    }
    public static Chat newInstance(ArrayList<String> param1,
                                   ArrayList<String> param2,
                                   ArrayList<String> param3,
                                   Double lat,
                                   Double lang) {
        Chat fragment = new Chat();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_PARAM1, param1);
        args.putStringArrayList(ARG_PARAM2, param2);
        args.putStringArrayList(ARG_PARAM3,param3);
        args.putDouble(ARG_PARAM4,lat);
        args.putDouble(ARG_PARAM5,lang);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            text =getArguments().getStringArrayList(ARG_PARAM1);
            keysSet=getArguments().getStringArrayList(ARG_PARAM2);
            userName =getArguments().getStringArrayList(ARG_PARAM3);
            Double lat =getArguments().getDouble(ARG_PARAM4);
            Double lang =getArguments().getDouble(ARG_PARAM5);
            currentLocation=new LatLng(lat,lang);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.chat_box);
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
        List<String> users = new ArrayList<>();
        input = text;
        keys=keysSet;
        users=userName;
        mAdapter = new ChatAdapter(getActivity().getApplicationContext(),input,keys,users,currentLocation);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

}
