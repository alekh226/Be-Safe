package project226.a000webhostapp.com.feelsecure;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchBar extends Fragment {

public EditText address;
public Button searchCrime;
    public SearchBar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search_bar, container, false);
        address =(EditText) view.findViewById(R.id.search_text);
        searchCrime =(Button)view.findViewById(R.id.search_button);
        searchCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask task =new getLocationFromAddressAsync(getActivity().getWindow().getContext());
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,address.getText().toString());

            }
        });
    return view;
    }

}
