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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CrimeFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<String> mParam1=new ArrayList<String>();
    private List<String> mParam2=new ArrayList<String>();

    public static CrimeFragment newInstance(ArrayList<String> param1, ArrayList<String> param2) {
        CrimeFragment fragment = new CrimeFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_PARAM1,param1);
        args.putStringArrayList(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getStringArrayList(ARG_PARAM1);
            mParam2 = getArguments().getStringArrayList(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_crime, container, false);
        /*TextView crime_text =(TextView)view.findViewById(R.id.crime_text);
        String crime =getArguments().getString("record");
        crime_text.setText(crime);*/

        recyclerView = (RecyclerView) view.findViewById(R.id.crime_text);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        //List<String> input = new ArrayList<String>();
        //List<String> keys = new ArrayList<String>();


        mAdapter = new MyCrimeAdapter(getActivity().getApplicationContext(),mParam1,mParam2);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

}
